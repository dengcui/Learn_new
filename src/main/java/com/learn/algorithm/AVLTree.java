package com.learn.algorithm;

public class AVLTree<T extends Comparable<T>> {
    private AVLTreeNode<T> mRoot;    // 根结点

    // 构造函数
    public AVLTree() {
        mRoot = null;
    }
    /**
     * 节点右旋（LL）
     * @param node 需要进行右旋的点
     * @return
     */
    public AVLTreeNode<T>  leftLeftRotation(AVLTreeNode<T> node){
        if(node!=null){
            //节点的左节点
            AVLTreeNode<T> leftNode = node.leftChild;
            //节点左子树的右子树变为节点的左子树
            node.leftChild = leftNode.rightChild;
            //节点本身变为左节点的右子树
            leftNode.rightChild = node;
            node.height = max( height(node.leftChild), height(node.rightChild)) + 1;
            leftNode.height = max( height(leftNode.leftChild), node.height) + 1;
            //返回左节点 认为左节点替代原来的节点
            return leftNode;
        }
        return null;
    }

    /**
     * 节点左旋(RR)
     * @param node 需要进行左旋的点
     * @return
     */
    public AVLTreeNode<T> rightRightRotation(AVLTreeNode<T> node){
        if(node!=null){
            //节点的右节点
            AVLTreeNode<T> rightNode = node.rightChild;
            //节点右子树的左子树变为节点的右子树
            node.rightChild = rightNode.leftChild;
            //节点本身变为左节点的右子树
            rightNode.leftChild = node;
            node.height = max( height(node.leftChild), height(node.rightChild)) + 1;
            rightNode.height = max( height(rightNode.rightChild), node.height) + 1;
            //返回右节点 认为右节点替代原来的节点
            return rightNode;
        }
        return null;
    }

    /**
     * 先左旋再右旋(LR)
     * @param node
     * @return
     */
    public AVLTreeNode<T> leftRightRotation(AVLTreeNode<T> node){
          //  System.out.println("LR");
            node.leftChild = rightRightRotation(node.leftChild);
            return leftLeftRotation(node);
    }


    /**
     * 先右旋再左旋(RL)
     * @param node
     * @return
     */
    public AVLTreeNode<T> rightLeftRotation(AVLTreeNode<T> node){
      //  System.out.println("RL");
        node.rightChild = leftLeftRotation(node.rightChild);
        return rightRightRotation(node);
    }

    /**
     * 插入
     * @param tree
     * @param key
     * @return
     */
    public AVLTreeNode<T> insert(AVLTreeNode<T> tree, T key){
        //树为空则新建节点
        if(tree == null){
            tree = new AVLTreeNode<>(null,null, key);
            if(tree == null){
                System.out.println("something happened...");
            }
        }else{
            //需要进行比较插入的节点和根节点的大小,平衡二叉树是左小右大的
            int cmp = key.compareTo(tree.val);
            //小于0说明要插入左子树
            if(cmp < 0) {
                //递归调用
                tree.leftChild = insert(tree.leftChild, key);
                //判断该节点是否失衡
                if (height(tree.leftChild) - height(tree.rightChild) == 2) {
                    //如果失衡判断它是小于该节点左子树还是右子树 看是被插入到那边
                    //如果是小于说明被插入到了左子树 失衡只要LL(单项右旋)
                    //如果被插入到了右子树说明要进行LR 先左旋再右旋
                    if (key.compareTo(tree.leftChild.val) < 0)
                        tree = leftLeftRotation(tree);
                    else
                        tree = leftRightRotation(tree);
                }
            }
            else if (cmp > 0){
                //递归调用
                tree.rightChild = insert(tree.rightChild, key);
                //判断该节点是否失衡
                if (height(tree.rightChild) - height(tree.leftChild) == 2) {
                   // System.out.println("进入这里"+tree.val+key);
                    //如果失衡判断它是小于该节点右子树还是右子树 看是被插入到那边
                    //如果是大于说明被插入到了右子树 失衡只要RR(单项左旋)
                    //如果被插入到了右子树说明要进行RL 先右旋再左旋
                    if (key.compareTo(tree.rightChild.val) > 0)
                        tree = rightRightRotation(tree);
                    else
                        tree = rightLeftRotation(tree);
                }
            }else{
                System.out.println("add failed...");
            }
        }
        tree.height = max( height(tree.leftChild), height(tree.rightChild)) + 1;
        return tree;
    }

    //删除某个节点
    public AVLTreeNode<T> removeNode(AVLTreeNode<T> tree, AVLTreeNode<T> delNode) {
        if(tree ==null || delNode == null)
            return null;
        //先比较要删除的节点是在tree的左还是右
        int cmp = delNode.val.compareTo(tree.val);
        if(cmp < 0)
        {
            //递归调用删除左子树的节点
            tree.leftChild = removeNode(tree.leftChild, delNode);
            //删除节点后要看是否发生平衡要进行调整
            if (height(tree.rightChild) - height(tree.leftChild) == 2) {
                AVLTreeNode<T> r =  tree.rightChild;
                if (height(r.leftChild) > height(r.rightChild))
                    tree = rightLeftRotation(tree);
                else
                    tree = rightRightRotation(tree);
            }
        }else if (cmp > 0){
            //递归调用删除右子树的节点
            tree.rightChild = removeNode(tree.rightChild, delNode);
            //删除节点后要看是否发生平衡要进行调整
            if (height(tree.leftChild) - height(tree.rightChild) == 2) {
                AVLTreeNode<T> r =  tree.leftChild;
                if (height(r.rightChild) > height(r.leftChild))
                    tree = leftRightRotation(tree);
                else
                    tree = leftLeftRotation(tree);
            }
        }else
        {
            //找到要删除的节点
            if ((tree.leftChild != null) && (tree.rightChild != null)) {
                // 如果tree的左子树比右子树高；
                // 则(01)找出tree的左子树中的最大节点
                //   (02)将该最大节点的值赋值给tree。
                //   (03)删除该最大节点。
                // 这类似于用"tree的左子树中最大节点"做"tree"的替身；
                // 采用这种方式的好处是: 删除"tree的左子树中最大节点"之后，AVL树仍然是平衡的。
                if(height(tree.leftChild) > height(tree.rightChild)){
                    AVLTreeNode<T> maxNode = maximum(tree);
                    tree.val = maxNode.val;
                    tree.leftChild = removeNode(tree.leftChild, maxNode);
                }else {
                    // 如果tree的左子树不比右子树高(即它们相等，或右子树比左子树高1)
                    // 则(01)找出tree的右子树中的最小节点
                    //   (02)将该最小节点的值赋值给tree。
                    //   (03)删除该最小节点。
                    // 这类似于用"tree的右子树中最小节点"做"tree"的替身；
                    // 采用这种方式的好处是: 删除"tree的右子树中最小节点"之后，AVL树仍然是平衡的。
                    AVLTreeNode<T> minNode = minimum(tree);
                    tree.val = minNode.val;
                    tree.rightChild = removeNode(tree.rightChild, minNode);
                }
            }else
            {
                //如果要删除节点的左右节点有一个不为空或者都为空则为另外一个节点
                tree = (tree.leftChild!=null) ? tree.leftChild : tree.rightChild;
            }
        }
        return tree;
    }

    /**
     * 从整个树中按照key移除某个节点
     * @param key
     */
    public void remove(T key) {
        AVLTreeNode<T> z;
        if ((z = search(mRoot, key)) != null)
            mRoot = removeNode(mRoot, z);
    }
    /*
     * 销毁AVL树
     */
    private void destroy(AVLTreeNode<T> tree) {
        if (tree==null)
            return ;
        if (tree.leftChild != null)
            destroy(tree.leftChild);
        if (tree.rightChild != null)
            destroy(tree.rightChild);
        tree = null;
    }

    public void destroy() {
        destroy(mRoot);
    }

    /*
     * 打印"二叉查找树"
     *
     * key        -- 节点的键值
     * direction  --  0，表示该节点是根节点;
     *               -1，表示该节点是它的父结点的左孩子;
     *                1，表示该节点是它的父结点的右孩子。
     */
    private void print(AVLTreeNode<T> tree, T key, int direction) {
        if(tree != null) {
            if(direction==0)    // tree是根节点
                System.out.printf("%2d is root\n", tree.val, key);
            else                // tree是分支节点
                System.out.printf("%2d is %2d's %6s child\n", tree.val, key, direction==1?"right" : "left");

            print(tree.leftChild, tree.val, -1);
            print(tree.rightChild,tree.val,  1);
        }
    }

    public void print() {
        if (mRoot != null)
            print(mRoot, mRoot.val, 0);
    }

    /*
     * 前序遍历"AVL树"
     */
    private void preOrder(AVLTreeNode<T> tree) {
        if(tree != null) {
            System.out.print(tree.val+" ");
            preOrder(tree.leftChild);
            preOrder(tree.rightChild);
        }
    }

    public void preOrder() {
        preOrder(mRoot);
    }

    /*
     * 中序遍历"AVL树"
     */
    private void inOrder(AVLTreeNode<T> tree) {
        if(tree != null)
        {
            inOrder(tree.leftChild);
            System.out.print(tree.val+" ");
            inOrder(tree.rightChild);
        }
    }

    public void inOrder() {
        inOrder(mRoot);
    }

    /*
     * 后序遍历"AVL树"
     */
    private void postOrder(AVLTreeNode<T> tree) {
        if(tree != null) {
            postOrder(tree.leftChild);
            postOrder(tree.rightChild);
            System.out.print(tree.val+" ");
        }
    }

    public void postOrder() {
        postOrder(mRoot);
    }

    /**
     * 找到树中最大的节点
     * @param tree
     * @return
     */
    public AVLTreeNode<T> maximum(AVLTreeNode<T> tree){
        if (tree == null)
            return null;

        while(tree.rightChild != null)
            tree = tree.rightChild;
        return tree;
    }

    /**
     * 找到整个树中最大的节点的值
     * @return
     */
    public T maximum() {
        AVLTreeNode<T> p = maximum(mRoot);
        if (p != null)
            return p.val;
        return null;
    }

    /**
     * 找到树中最小的节点
     * @param tree
     * @return
     */
    public AVLTreeNode<T> minimum(AVLTreeNode<T> tree){
        if (tree == null)
            return null;

        while(tree.leftChild != null)
            tree = tree.leftChild;
        return tree;
    }

    /**
     * 找到整个树中最小的节点的值
     * @return
     */
    public T minimum() {
        AVLTreeNode<T> p = minimum(mRoot);
        if (p != null)
            return p.val;
        return null;
    }


    public void insert(T key) {
        mRoot = insert(mRoot, key);
    }
    /*
     * (递归实现)查找"AVL树x"中键值为key的节点
     */
    private AVLTreeNode<T> search(AVLTreeNode<T> x, T key) {
        if (x==null)
            return x;

        int cmp = key.compareTo(x.val);
        if (cmp < 0)
            return search(x.leftChild, key);
        else if (cmp > 0)
            return search(x.rightChild, key);
        else
            return x;
    }

    public AVLTreeNode<T> search(T key) {
        return search(mRoot, key);
    }

    private int max(int a, int b) {
        return a>b ? a : b;
    }

    /**
     * 返回树的高度
     * @param tree
     * @return
     */
    private int height(AVLTreeNode<T> tree){
        if(tree == null){
            return 0;
        }
        return tree.height;
    }

    public int height() {
        return height(mRoot);
    }


    class AVLTreeNode<T extends Comparable<T>> {
        T val;
        AVLTreeNode<T> leftChild;    // 左孩子
        AVLTreeNode<T> rightChild;    // 右孩子
        int height;

        public AVLTreeNode(AVLTreeNode<T> leftChild, AVLTreeNode<T> rightChild, T val){
            this.leftChild = leftChild;
            this.rightChild = rightChild;
            this.val = val;
            this.height = 0;
        }
    }
}
