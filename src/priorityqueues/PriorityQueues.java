package priorityqueues;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;


    public class PriorityQueues <T extends Comparable<T>>{

    
   private List<T> heap; // to store the nodes.
   // to know node that has a certin value when we want to remove it (with O(1) time complexity)
   private Map<T,TreeSet<Integer>> hashMap = new HashMap(); 
   
   public PriorityQueues(int size){
       this.heap = new ArrayList<T>(size);
   }
   
   public T peek(){ if(heap.size() != 0){return heap.get(0);}return null;}
   
   public T poll(){ return remove(heap.get(0));}
   
   public boolean contains(T value){
       return hashMap.containsKey(value);
   }
   
   public void add(T value){
       
       // add the new value in the heap and in the hashMap 
       // because the adding will be in the ned then we need to swim untill
       // the heap variant is satisfied.
       heap.add(value);
       addToMap(value,heap.size()-1);
       // the index of the new node will be the size of the heap -1 (0 based)
       swim(heap.size() - 1);
   }
   
   public T remove(T value){
       
       // get the index of the element that we want to remove 
       // 
       Integer  valueIndex = getFromMap(value);
       // swap the lement that we want to remove with the last node 
       //  sw we can remove it.
       if (valueIndex != null) {
           swap(valueIndex, heap.size()-1);
       // remove frin the heap.
       heap.remove(value);
       //remove from the map.
       removeFromMap(value, heap.size()-1);
       if (heap.size() == 0) {
           return  value;
       }
       
       // we dont know what we should do swimm or sink because  it's based on 
       // the value so we  will test first sink if nothing change then we try
       // swim and in this case if nothing change then the tree is balanced by
       // you very good luck hhhh.
       
       T nodeToTestWith = heap.get(valueIndex);
       // try sink
       sink(valueIndex);
       // check if the sink works and try swim if not.
       if (heap.get(valueIndex).equals(nodeToTestWith)) {
           swim(valueIndex);
       }  
       
       return value;    
       }
       return null;
       
   }
   
   
   // map helper methodes :
   public void mapSwapping(T value1, T value2, int index1, int index2){
       
       // get all the set of the numbers that has the same priority
       TreeSet<Integer> value1Set = hashMap.get(value1);
       TreeSet<Integer> value2Set = hashMap.get(value2);
       
       // remove the nidex of the tow elements that we want to swap so each one will 
       // be added later in the other ones set
       value1Set.remove(index1);
       value2Set.remove(index2);
       
       // know add the elements that we removed but reverse the sets 
       value1Set.add(index2);
       value2Set.add(index1);
       
   }
   
   public void removeFromMap(T value , int index){
       
       TreeSet<Integer> valueSet = hashMap.get(value);
       valueSet.remove(index);
       // check if the set of that contained the value that we removed is empty now
       // in that cas we can remove the whole set from the map.
       if(valueSet.size() == 0){hashMap.remove(valueSet);}
   }
   
   public Integer getFromMap(T value){
       
       TreeSet<Integer> valueSet = hashMap.get(value);
       if(valueSet != null){return valueSet.last();}
       return null;
   }
   
   public void addToMap(T value, int index){
       
       // check if the value already exist then we add it to the already existing
       // treeSet if not we create a new one.
       
       TreeSet valueSet = hashMap.get(value);
       if (valueSet == null) {
           TreeSet newSet = new TreeSet();
           newSet.add(index);
           hashMap.put(value, newSet);   
       }else{
           valueSet.add(index);
       }
   }
   
   // priority queue balancing methodes 
   
   public void swap(int indexI, int indexJ){
       
       // we need to swap in both the heap (list) and the map .
       T heapElementAtIndexI = heap.get(indexI);
       T heapElementAtIndexJ = heap.get(indexJ);
       
       // swap indide the list
       heap.set(indexJ, heapElementAtIndexI);
       heap.set(indexI, heapElementAtIndexJ);
       // swap in the map using the methode that we defiend.
       mapSwapping(heapElementAtIndexI, heapElementAtIndexJ, indexI, indexJ);
   }
   
   public boolean isSmaller(int indexI,int indexJ){
       
       T indexIValue = heap.get(indexI);
       T indexJValue = heap.get(indexJ);
       
       return indexIValue.compareTo(indexJValue) <= 0;
   }
   
   public void sink(int newAdedValueIndex){
       
       // keep sinking in the heap untill the heap is balanced
       while(true){
           int leftChildIndex = 2*newAdedValueIndex+1;
           int rightChildIndex = 2*newAdedValueIndex+2;
           int smallestChildIndex = leftChildIndex;  // just before testing
           
           // check the smallest one from between left and right (here we are creating a min heap).
           if(rightChildIndex < heap.size() && isSmaller(rightChildIndex, leftChildIndex)){
               smallestChildIndex = rightChildIndex;
           }else{
               if (leftChildIndex >= heap.size() || isSmaller(newAdedValueIndex, leftChildIndex)) {
                    break;
               }
           }
           
           // swap the nodes in case we have to.
           swap(newAdedValueIndex, smallestChildIndex);
           // change the index of the node that we are balancing bazed on the swap that we did.
           newAdedValueIndex = smallestChildIndex;
       }
   }
   
   
   public void swim(int newAddedNodeIndex){
       
       // get the parent index then swim in the heap unitl 
       // the condition is verified or we reach the end level
       int parentIdnex = (newAddedNodeIndex -1)/2;
       System.err.println(" value is " + parentIdnex);
       
       while(newAddedNodeIndex > 0 && isSmaller(newAddedNodeIndex, parentIdnex)){
           // swap the node because the condition is not satisfied.
           // go dwon by one level and re do the same think in the loop.
           swap(parentIdnex , newAddedNodeIndex);
           newAddedNodeIndex = parentIdnex;
           parentIdnex = (newAddedNodeIndex -1)/2;
       }
               
   }
   
   public static void main(String args[]){
       
       PriorityQueues pq = new PriorityQueues(5);
       pq.add(22);
       pq.add(2);
       pq.add(0);
       pq.add(10);
       pq.add(-1);
        System.out.println(" result is " +  pq.peek());
       pq.remove(-1);
       pq.remove(-99);
       pq.remove(0);
       pq.remove(2);
       pq.remove(10);
       pq.remove(22);
       System.out.println(" result is " +  pq.peek());
         
       
       
       
   } 
    
    
    
}
