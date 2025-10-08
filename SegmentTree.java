public class SegmentTree {

        private Node root;

        // 🔹 Inner Node class
        private static class Node {
            int data;
            int startInterval;
            int endInterval;
            Node left;
            Node right;

            public Node(int startInterval, int endInterval) {
                this.startInterval = startInterval;
                this.endInterval = endInterval;
            }
        }

        // 🔹 Constructor — builds tree
        public SegmentTree(int[] arr) {
            this.root = constructTree(arr, 0, arr.length - 1);
        }

        // 🔹 Build function
        private Node constructTree(int[] arr, int start, int end) {
            // Base case: leaf node
            if (start == end) {
                Node leaf = new Node(start, end);
                leaf.data = arr[start];
                return leaf;
            }

            // Recursive case
            Node node = new Node(start, end);
            int mid = (start + end) / 2;

            node.left = constructTree(arr, start, mid);
            node.right = constructTree(arr, mid + 1, end);

            // store sum
            node.data = node.left.data + node.right.data;

            return node;
        }

        // 🔹 Display the segment tree
        public void display() {
            display(this.root);
        }

        private void display(Node node) {
            if (node == null) return;

            String str = "";
            if (node.left != null)
                str += "Left[" + node.left.startInterval + "-" + node.left.endInterval + " => " + node.left.data + "] ";
            else
                str += "No left ";

            str += " <- [ " + node.startInterval + "-" + node.endInterval + " => " + node.data + " ] -> ";

            if (node.right != null)
                str += "Right[" + node.right.startInterval + "-" + node.right.endInterval + " => " + node.right.data + "]";
            else
                str += "No right";

            System.out.println(str);

            display(node.left);
            display(node.right);
        }

        // 🔹 Query function
        public int query(int qsi, int qei) {
            return this.query(this.root, qsi, qei);
        }

        private int query(Node node, int qsi, int qei) {
            // completely inside
            if (node.startInterval >= qsi && node.endInterval <= qei) {
                return node.data;
            }

            // completely outside
            else if (node.endInterval < qsi || node.startInterval > qei) {
                return 0;
            }

            // partial overlap
            else {
                int leftAns = query(node.left, qsi, qei);
                int rightAns = query(node.right, qsi, qei);
                return leftAns + rightAns;
            }
        }

        // 🔹 Update function (change element value)
        public void update(int index, int newValue) {
            update(this.root, index, newValue);
        }

        private void update(Node node, int index, int newValue) {
            if (node.startInterval == node.endInterval) {
                node.data = newValue;
                return;
            }

            int mid = (node.startInterval + node.endInterval) / 2;

            if (index <= mid) {
                update(node.left, index, newValue);
            } else {
                update(node.right, index, newValue);
            }

            // update parent after child changes
            node.data = node.left.data + node.right.data;
        }

        // 🔹 Main function to test
        public static void main(String[] args) {
            int[] arr = {3, 8, 6, 7, -2, -8, 4, 9};

            SegmentTree tree = new SegmentTree(arr);

            System.out.println("Segment Tree Display:");
            tree.display();

            System.out.println("\nQuery(2,5) => " + tree.query(2, 5)); // expect 3
            System.out.println("Query(0,3) => " + tree.query(0, 3)); // expect 24

            System.out.println("\nUpdating index 2 to 10...");
            tree.update(2, 10);

            System.out.println("After Update:");
            tree.display();

            System.out.println("\nQuery(2,5) after update => " + tree.query(2, 5));
        }
    }


