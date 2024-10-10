import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;

public class MaxHeap
{
   private ArrayList<Student> students;
   
   public MaxHeap(int capacity)
   {
      students = new ArrayList<Student>(capacity);
   }
      
   public MaxHeap(Collection<Student> collection)
   {
      students = new ArrayList<Student>(collection);
      for(int i = size()/2 - 1; i >= 0; i--)
      {
         maxHeapify(i);
      }
      fixIndices();
   }
   
   //Abstraction Breaking thingy
   public void fixIndices() {
	   for (int i = 0; i<students.size(); i++) {
		   students.get(i).setIndex(i);
	   }
   }
   
   public Student getMax()
   {
      if(size() < 1)
      {
         throw new IndexOutOfBoundsException("No maximum value:  the heap is empty.");
      }
      return students.get(0);
   }
   
   public Student extractMax()
   {
      Student value = getMax();
      students.set(0,students.get(size()-1));
      students.remove(size()-1);
      maxHeapify(0);
      return value;
   }
    
   public int size()
   {
      return students.size();
   }
   
   private void bubble(int index) {
	   int parent = parent(index);
	   if (students.get(index).compareTo(students.get(parent))>0) {
		   swap(index,parent);
		   bubble(parent);
	   }
   }
   
   public void insert(Student elt)
   {
	  int lastIndex = size();
	  students.add(lastIndex, elt);
	  elt.setIndex(lastIndex);
      System.out.println("Last student: " + students.get(lastIndex).getName() + " at index: " + lastIndex);
	  bubble(lastIndex);
   }
   
   /* deprecated in favor of abstraction breaking
    * 
   private int find(Student elt) {
	  for (int i = 0; i<size(); i++) {
		  if (students.get(i)==elt) {
			  return i;
		  }
	  }
	  return -1;
   }
   */
   
   public void addGrade(Student elt, double gradePointsPerUnit, int units)
   {
	  //int i = find(elt); old, non abstraction breaking
	  int i = elt.getIndex();
	  if (i==-1) return;
	  elt.addGrade(gradePointsPerUnit, units);
	  if (students.get(i).compareTo(students.get(parent(i)))>0) {
		  bubble(i);
	  } else {
		  maxHeapify(i);
	  }
	//Check if there is an increase in GPA by adding the new units
	//boolean increase = (students.get(i).gpa() < gradePointsPerUnit) ? true : false;
   }
   
   private int parent(int index)
   {
      return (index - 1)/2;
   }
   
   private int left(int index)
   {
      return 2 * index + 1;
   }
   
   private int right(int index)
   {
      return 2 * index + 2;
   }
   
   private void swap(int from, int to)
   {
	  //Abstraction Breaking time
	  students.get(from).setIndex(to);
	  students.get(to).setIndex(from);
	  //
	   
      Student val = students.get(from);
      students.set(from,  students.get(to));
      students.set(to,  val);
   }
   
   private void maxHeapify(int index)
   {
      int left = left(index);
      int right = right(index);
      int largest = index;
      if (left <  size() && students.get(left).compareTo(students.get(largest)) > 0)
      {
         largest = left;
      }
      if (right <  size() && students.get(right).compareTo(students.get(largest)) > 0)
      {
         largest = right;
      }
      if (largest != index)
      {
         swap(index, largest);
         maxHeapify(largest);
      }  
   }  
   /* imagine debugging with a main method lol
    * 
    *  public static void main(String[] args) {
	   MaxHeap heap = new MaxHeap(10);
	   Student susan = new Student("Susan", 5, 60);
	   heap.insert(susan);
	   heap.insert(new Student("Bob", 4, 60));
	   heap.insert(new Student("Carl", 4, 60));
	   heap.insert(new Student("Jean", 4, 60));
	   heap.insert(new Student("Danny", 4, 60));
	   System.out.println(heap.getMax().getName() + ": " + heap.getMax().gpa());
	   heap.addGrade(susan, 1, 100);
	   System.out.println(heap.getMax().getName() + ": " + heap.getMax().gpa());
   }*/
}