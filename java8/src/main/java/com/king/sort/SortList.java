package com.king.sort;

public class SortList {
	/**
     * @param args
     */
    public ListNode Merge(ListNode first,ListNode second){   //合并两个有序链表，并返回新链表的投结点
        ListNode rear;
        ListNode head;
        rear=head=new ListNode(-1);
        
        while(first!=null&&second!=null){
            if(first.val<=second.val){
                rear.next=first;
                rear=first;
                first=first.next;
            }
            else{
                rear.next=second;
                rear=second;
                second=second.next;
            }
        }
        if(first!=null)
            rear.next=first;
        else
            rear.next=second;
        
        return head.next;
    }
    
    public ListNode sortList(ListNode head){     
        /*
         * 实现链表的合并排序：1、将链表划分成基本相等的两个链表
         * 2、递归将这两个链接继续划分，直到链表的长度为0或者1为止
         * 3、调用Merge（）将链接进行合并
         */
        
        if(head==null||head.next==null)
            return head;
        ListNode mid =head;
        ListNode pos =mid.next;
        while(pos!=null){
            pos=pos.next;
            if(pos!=null){
                pos=pos.next;
                mid=mid.next;
            }
        }
        ListNode q=sortList(mid.next);
        mid.next=null;
        return Merge(sortList(head), q);
    }
    
    
    
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        ListNode first1 = new ListNode(0);
        ListNode rear1 =first1;
        
        for(int i=9;i>=1;i--){
            ListNode q= new ListNode(i);
            rear1.next=q;
            rear1=q;
        }
        ListNode q=first1;
        while(q!=null){
            System.out.print(q.val+ ",");
            q=q.next;
        }
        System.out.println();
        SortList sl = new SortList();
        sl.sortList(first1);
        
        ListNode p=first1;
        while(p!=null){
            System.out.print(p.val+ ",");
            p=p.next;
        }
        System.out.println();
    }
   
}
class ListNode{
    int val;
    ListNode next=null;
    public ListNode(){
    }
    public ListNode(int val){
        this.val=val;
    }
}
