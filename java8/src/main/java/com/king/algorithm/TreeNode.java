package com.king.algorithm;

import lombok.Data;
import org.junit.jupiter.api.Test;
import sun.reflect.generics.tree.Tree;

import java.util.ArrayList;
import java.util.List;



@Data
public class TreeNode {
    private String name;
    private TreeNode leftLeaf;
    private TreeNode rightLeaf;
    @Test
    public void  testFind()
    {
        List<TreeNode> nodeList= new ArrayList<>();

        TreeNode root= new TreeNode();
        nodeList.add(root);
        root.setName("root");

        TreeNode leftLeaf=new TreeNode();
        root.setLeftLeaf(leftLeaf);
        leftLeaf.setName("First LeftLeaf\t");

        TreeNode rightLeaf=new TreeNode();
        root.setRightLeaf(rightLeaf);
        rightLeaf.setName("Right LeftLeaf");




        TreeNode secondRightLeaf=new TreeNode();
        secondRightLeaf.setName("第三层右节点");
        leftLeaf.setRightLeaf(secondRightLeaf);

        TreeNode secondLeftLeaf=new TreeNode();
        secondLeftLeaf.setName("第三层左节点");
        rightLeaf.setRightLeaf(secondLeftLeaf);


        TreeNode thirdLeftLeaf=new TreeNode();
        thirdLeftLeaf.setName("第四层左节点");
        secondRightLeaf.setRightLeaf(thirdLeftLeaf);
        print(nodeList);
    }

    private void print (List<TreeNode> nodeList)
    {
        if(null==nodeList || nodeList.size()==0)
        {
            return ;
        }
        List<TreeNode> child=new ArrayList<>();
        for( TreeNode node : nodeList)
        {
            System.out.print(node.name);
            if(null!=node.getLeftLeaf()) {
                child.add(node.getLeftLeaf());
            }
            if(null!=node.getRightLeaf()) {
                child.add(node.getRightLeaf());
            }
        }
        System.out.println();
        print(child);
    }

}
