import java.util.Random;

class operation{
    public char operator;
    public operation(char a){
        operator = a;
    }
}

class expression_tree{
    public int value; //value of the expression
//    public int height; //height of the tree
    public int fitness; //value of fitness function
    public operation head; //operator which is situated on the top of the tree
    public expression_tree left; //left part of the tree
    public expression_tree right; //right part of the tree

    /* function for calculating the value of expression*/
    public int calculate_value(){
            if ((head.operator=='@')){
                return value;
            }
            else if (head.operator=='+'){
                value = left.calculate_value()+right.calculate_value();
                return value;
            }
            else if (head.operator=='-'){
                return left.calculate_value()-right.calculate_value();
            }
            else if (head.operator=='*'){
                return left.calculate_value()*right.calculate_value();
            }
            else{
                return left.calculate_value()/right.calculate_value();
            }

        }
    public int calculate_fintess(){
        fitness = Math.abs(35-value);
        return fitness;
    }
    public expression_tree(operation a){
        head = a;
    };
    public expression_tree(int val){
        value = val;
    }
    public expression_tree(){}
    }

public class Main {
    static expression_tree[] generation = new expression_tree[32];
    static operation four_operations(){
        double k = Math.random();
        if (k<2.5){
            operation new_one = new operation('+');
            return new_one;
        }
        else if (k<5){
            operation new_one = new operation('-');
            return new_one;
        }
        else if (k<7.5){
            operation new_one = new operation('*');
            return new_one;
        }
        else {
            operation new_one = new operation('/');
            return new_one;
        }
    }
    static operation five_operations(){
        double k = Math.random();
        if (k<2){
            operation new_one = new operation('+');
            return new_one;
        }
        else if (k<4){
            operation new_one = new operation('-');
            return new_one;
        }
        else if (k<6){
            operation new_one = new operation('*');
            return new_one;
        }
        else if (k<8){
            operation new_one = new operation('/');
            return new_one;
        }
        else{
            operation new_one = new operation('/');
            return new_one;
        }
    }
    static expression_tree create_subtree(int height){
        if (height == 0){
            int val = (int)(Math.random()*10);
            expression_tree new_one = new expression_tree(val);
            new_one.head = new operation('@');
            return new_one;
        }
        else {
            int new_height = (int)(Math.random()*10%(height));
            expression_tree new_one = new expression_tree(four_operations());
            new_one.left = create_subtree(new_height);
            new_one.right = create_subtree(new_height);
            return new_one;
        }
    }
    static void first_generation(){
        for (int i = 0; i<32; i++){
            int height = (int)(Math.random()*10%6);
            expression_tree new_one= create_subtree(height);
            generation[i] = new_one;

        }
    }
    static void fitness(){
        for (int i = 0; i<32; i++){
            generation[i].calculate_value();
            generation[i].calculate_fintess();
        }
    }
    static expression_tree crosover(expression_tree tree1, expression_tree tree2){
        int side =  (int)Math.round(Math.random());
        if (side < 2.5){
            tree1.left = tree2.left;
        }
        else if (side<5){
            tree1.left = tree2.right;
        }
        else if (side<7.5){
            tree1.right = tree2.left;
        }
        else{
            tree1.right = tree2.right;
        }
        return mutate(tree1);
    }
    static expression_tree mutate(expression_tree tree){
        expression_tree cur = tree;
        while (!(cur.head.operator=='@')){
            int side = (int)(Math.round(Math.random()));
            int change = (int)(Math.round(Math.random()));
            if (change==1){
                cur.head = four_operations();
            }
            if (side ==0){
                cur = cur.left;
            }
            else{
                cur = cur.right;
            }
        }
        cur.value = (int)(Math.random()*10);
        return tree;
    }
    static void sort(){
        for (int i = 0; i<32; i++){
            for (int j = 1; j<32; j++){
                if(generation[j].fitness<generation[j-1].fitness){
                    expression_tree temp = generation[j];
                    generation[j] = generation[j-1];
                    generation[j-1] = temp;

                }
            }
        }
    }
    static void selecton(){
        for (int i = 16, j = 0; i<32; i++, j++){
            generation[i] = crosover(generation[j],generation[j+1]);
        }
    }

    public static void main(String[] args) {
        first_generation();
        for (int i = 0; i<200; i++){
            fitness();
            sort();
            selecton();
        }
        sort();
        System.out.println(generation[0].value);
    }
}
