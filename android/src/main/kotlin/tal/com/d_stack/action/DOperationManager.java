package tal.com.d_stack.action;

import tal.com.d_stack.DStack;
import tal.com.d_stack.channel.DStackMethodHandler;
import tal.com.d_stack.node.DNode;
import tal.com.d_stack.node.DNodeManager;
import tal.com.d_stack.node.DNodeResponse;
import tal.com.d_stack.node.constants.DNodeActionType;
import tal.com.d_stack.node.constants.DNodePageType;
import tal.com.d_stack.router.INodeOperation;
import tal.com.d_stack.utils.DLog;

/**
 * 节点操作行为记录
 */
public class DOperationManager {
    public static void operation(DNode node) {
        if (!DStack.getInstance().isOpenNodeOperation()) {
            return;
        }
        if (!node.isBoundary() &&
                node.getPageType().equals(DNodePageType.DNodePageTypeFlutter)
                && node.getAction().equals(DNodeActionType.DNodeActionTypePop)) {
            //操作一个flutter页面如果是pop行为，不是临界页面，不记录操作，等didPop消息
            return;
        }
        DNodeResponse nodeResponse = DNodeManager.getInstance().createNodeResponse(node);
        DLog.logE("$$$$$节点操作$$$$$");
        DLog.logE(nodeResponse.action + "-----" + nodeResponse.target);
        DLog.logE("$$$$$节点操作$$$$$");
        DStackMethodHandler.sendNodeOperation(nodeResponse);
        INodeOperation nodeOperation = DStack.getInstance().getNodeOperation();
        if (nodeOperation != null) {
            nodeOperation.operationNode(nodeResponse);
        }
    }
}
