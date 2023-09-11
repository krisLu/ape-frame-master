package com.jingdianjichi.tool.tree;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 功能描述：树形数据构建工具
 *
 * @author tuojinhui
 * @version 1.0
 */
public class TreeUtils {

    /**
     * 方法描述：初始化树。此处不进行节点层级排序，排序由业务处理
     *
     * @param nodes 节点数据
     * @return java.util.List<TreeNode>
     */
    public static <T extends TreeNode> List<T> buildTree(List<T> nodes) {
        Map<String, List<TreeNode>> groups = (nodes = c(nodes)).stream().collect(Collectors.groupingBy(TreeNode::getParentId, LinkedHashMap::new, Collectors.toList()));
        return nodes.stream().filter(Objects::nonNull).peek(pnd -> pnd.setChildren(groups.get(pnd.getId()))).filter(TreeNode::getRootNode).collect(Collectors.toList());
    }

    /**
     * 方法描述：防止空指针异常处理
     *
     * @param c 集合数据
     * @return java.util.List<L>
     */
    private static <L> List<L> c(List<L> c) {
        return Optional.ofNullable(c).orElseGet(Collections::emptyList);
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    @Accessors(chain = true)
    private static class Node extends TreeNode {
        private String id;
        private String parentId;
        private String name;
        private String sort;
    }

    /**
     * 验证代码，请勿调用，验证后可删除
     */
    public static void main(String[] args) throws JsonProcessingException {
        List<Node> nodeList = Arrays.asList(
                new Node().setId("1").setParentId("0").setName("权限管理"),
                new Node().setId("2").setParentId("1").setName("用户管理"),
                new Node().setId("3").setParentId("1").setName("角色管理"),
                new Node().setId("4").setParentId("1").setName("部门管理"),
                new Node().setId("5").setParentId("1").setName("菜单管理"),

                new Node().setId("6").setParentId("0").setName("日志管理"),
                new Node().setId("7").setParentId("6").setName("登录日志"),
                new Node().setId("8").setParentId("6").setName("操作日志"),
                new Node().setId("9").setParentId("6").setName("审计日志"),

                new Node().setId("10").setParentId("0").setName("客户管理"),
                new Node().setId("11").setParentId("10").setName("新增客户"),
                new Node().setId("12").setParentId("10").setName("修改客户"),

                new Node().setId("13").setParentId("0").setName("供货商管理"),
                new Node().setId("14").setParentId("13").setName("新增供货商"),
                new Node().setId("15").setParentId("13").setName("修改供货商")

        );

        // 数据洗牌10次
        for (int i = 0; i < 10; i++) {
            Collections.shuffle(nodeList);
        }

        long start = System.currentTimeMillis();
        List<Node> nodes = TreeUtils.buildTree(nodeList);
        long end = System.currentTimeMillis();

        System.out.println(end - start);
        System.out.println(new ObjectMapper().writeValueAsString(nodes));
    }

}
