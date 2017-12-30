package com.king.algorithm;

import org.junit.jupiter.api.Test;

public class Hanoi {
    @Test
    public void test()
    {
        testHanoi(2,"A","B","C");
    }
    public void testHanoi(int n,String x,String y,String z)
    {
        if(n==1)
        {
            System.out.println(x+"------->"+z);
        }else {
            //将n-1个盘子从x移至y
            testHanoi(n - 1, x,z,y );
            //将最底下的盘子从x移至z
            System.out.println(x+"------->"+z);
            //将y上的n-1个盘子移至z上
            testHanoi(n - 1, y,x,z);
        }
    }
}
