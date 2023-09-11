package com.jingdianjichi.tool.tree;

import lombok.Getter;

import java.util.List;
import java.util.Objects;

/**
 * 功能描述：树节点
 *
 * @author tuojinhui
 * @version 1.0
 */
public abstract class TreeNode {

    /**
     * 当前节点id
     */
    public abstract String getId();

    /**
     * 父级节点id
     */
    public abstract String getParentId();

    /**
     * 是否根节点
     */
    @Getter
    private Boolean rootNode;

    /**
     * 是否叶子节点
     **/
    @Getter
    private Boolean leafNode;

    /**
     * 子节点数据
     **/
    @Getter
    private List<TreeNode> children;

    /**
     * 设置子节点数据，设置为protected禁止外部调用
     **/
    protected void setChildren(List<TreeNode> children) {
        this.children = children;
        this.rootNode = Objects.equals(getParentId(), "0");
        this.leafNode = children == null || children.isEmpty();
    }

}
