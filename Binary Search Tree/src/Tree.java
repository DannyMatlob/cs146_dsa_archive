public class Tree {
	private class Node{
		public int value;
		public Node left = null;
		public Node right = null;
		public Node parent = null;
		
		public Node(int i) {
			value = i;
		}
	}
	
	public Node root = null;
	public Tree () {
		
	}
	
	//Insert Method
	public void insert(int x) {
		insertRecurse(x, root);
	}
	
	public void insertRecurse(int x, Node n) {
		if (n==null) {
			root = new Node(x);
			return;
		}
		if (x<n.value) {
			if (n.left==null) {
				Node temp = new Node(x);
				n.left = temp;
				temp.parent = n;
				return;
			} else {
				insertRecurse(x,n.left);
			}
		} else {
			if (n.right==null) {
				Node temp = new Node(x);
				n.right = temp;
				temp.parent = n;
				return;
			} else {
				insertRecurse(x,n.right);
			}
		}
	}
	//End of Insert Method

	//Rotate Methods
	public void rotateLeft(Node n) {
		Node parent = n.parent;
		Node oldLeft = n.left;
		
		if (parent==root) root = n;
		if (parent.parent!=null) parent.parent.left = n;
		n.left = parent;
		n.parent = parent.parent;
		parent.parent = n;
		parent.right = oldLeft;
	}
	
	public void rotateRight(Node n) {
		Node parent = n.parent;
		Node oldRight = n.right;
		
		if (parent==root) root = n;
		if (parent.parent!=null) parent.parent.right = n;
		n.right = parent;
		n.parent = parent.parent;
		parent.parent = n;
		parent.left = oldRight;
	}
	//End of Rotate Methods
	
	//Size Method
	public int size() {
		return size(0);
	}
	public int size(int x) {
		Node n = search(x);
		return sizeRecurse(n);
	}
	
	public int sizeRecurse(Node n) {
		if (n==null) return 0;
		return 1 + sizeRecurse(n.left) + sizeRecurse(n.right);
	}
	//End of Size Method
	
	//Search Method
	public Node search(int x) {
		return searchRecurse(x, root);
	}
	
	public Node searchRecurse(int x, Node n) {
		if (n!=null && n.value==x) return n;
		if (n!=null && x<n.value) return searchRecurse(x, n.left);
		if (n!=null && x>=n.value) return searchRecurse(x,n.right);
		return null;
	}
	//End of Search Method
	
	//Get Method
	int getCounter = 0;
	int getValue = 0;
	public int get(int x) {
		getCounter = 0;
		getValue = 0;
		getRecurse(x, root);
		return getValue;
	}
	
	public void getRecurse(int x, Node n) {
		if (n==null || getCounter==x+1) return;
		getRecurse(x, n.left);
		getCounter++;
		if (getCounter == x+1) {
			getValue=n.value;
			return;
		}
		getRecurse(x,n.right);
	}
	//End of Get Method
	
	//ToString Method
	public void printTree() {
		printTreeRecurse(root, "");
	}
	public void printTreeRecurse(Node n, String indent) {
		if (n==null) return;
		printTreeRecurse(n.left,indent + "|  ");
		printTreeRecurse(n.right,indent + "|  ");
		System.out.println(indent + n.value);
	}
	//End of ToString Method
	
	public static void main(String[] args) {
		Tree t = new Tree();
		/*
		t.insert(1);
		t.insert(2);
		t.insert(3);
		t.insert(4);
		t.insert(5);
		t.insert(6);
		t.insert(7);
		t.insert(8);
		t.insert(9);
		t.insert(10);
		*/
		t.insert(10);
		t.insert(9);
		t.insert(8);
		t.insert(7);
		t.insert(6);
		t.insert(5);
		t.insert(4);
		t.insert(3);
		t.insert(2);
		t.insert(1);
		
		t.printTree();
		t.rotateRight(t.search(5));
		System.out.println();
		t.printTree();
	}
}
