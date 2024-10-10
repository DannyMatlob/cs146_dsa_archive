import java.util.ArrayList;
import java.util.Collections;

public class Tree {

	public Node root;

	public boolean insert(int x) {
		//Empty Tree case
		if (root == null) {
			root = new Node(x);
			return true;
		} else {
			return root.insert(x);
		}
	}

	public int size() {
		return root.size();
	}

	public int size(int x) {
		return root.size(x);
	}

	public int get(int x) {
		return root.get(x);
	}


	class Node {
		int size = 1;
		Node parent = null;
		ArrayList<Integer> keys = new ArrayList<Integer>();
		ArrayList<Node> children = new ArrayList<Node>();

		public Node(int key) {
			keys.add(key);
		}

		public int get(int index) {

		}

		public int size() {
			int counter = keys.size();
			for (Node child : children) {
				counter += child.size();
			}
			return counter;
		}

		public int size(int key) {
			if (keys.contains(key)) return this.size();
			if (isLeaf()) return 0;
			for (int i = 0; i < keys.size(); i++) {
				if (key < keys.get(i)) {
					return children.get(i).size(key);
				}
			}
			if (children.size() > keys.size()) {
				return children.get(keys.size()).size(key);
			}
			return 0;
		}



		public boolean insert(int key) {
			//Ignore duplicates
			if (keys.contains(key)) return false;
			//If leaf, insert at that node. Could potentially split node
			if (isLeaf()) {
				keys.add(key);
				Collections.sort(keys);
				split();
				return true;
			} else {
				for (int i = 0; i < keys.size(); i++) {
					if (key < keys.get(i)) {
						return children.get(i).insert(key);
					}
				}
				return children.get(keys.size()).insert(key);
			}
		}

		public void split() {
			//Check if Node has 3 keys
			if (!isFull()) return;

			//Splitting with no parent
			if (parent == null) {
				Node tempL = new Node(keys.get(0));
				Node tempR = new Node(keys.get(2));
				tempL.parent = this;
				tempR.parent = this;
				keys.remove(2);
				keys.remove(0);
				children.add(tempL);
				children.add(tempR);
				if (children.size() == 4) {
					tempL.children.add(children.get(0));
					tempL.children.add(children.get(1));
					tempR.children.add(children.get(2));
					tempR.children.add(children.get(3));
				}
				return;
			}

			//Splitting with a parent
			Node tempL = new Node(keys.get(0));
			Node tempR = new Node(keys.get(2));
			tempL.parent = this.parent;
			tempR.parent = this.parent;
			parent.keys.add(keys.get(1));
			Collections.sort(parent.keys);
			parent.children.add(tempL);
			parent.children.add(tempR);
			//sort these children
			parent.children.remove(this);



			//In case of cascading/big split
			parent.split();
		}

		public boolean isLeaf() {
			return children.size() == 0;
		}

		public boolean isFull() {
			return keys.size() == 3;
		}
	}
}