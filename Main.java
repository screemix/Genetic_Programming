import java.util.Random;

class operation{
    public char operator;
    public operation(char a){
        operator = a;
    }
}

class expression_tree{
    public int value;
    public int height;
    public int fitness;
    public operation head;
    public expression_tree left;
    public expression_tree right;
    public int calculate_value(){
        if (head==null){
            return value;
        }
        else {
            if (head.equals('+')){
                return left.calculate_value()+right.calculate_value();
            }
            else if (head.equals('-')){
                return left.calculate_value()-right.calculate_value();
            }
            else if (head.equals('*')){
                return left.calculate_value()*right.calculate_value();
            }
            else{
                return left.calculate_value()/right.calculate_value();
            }
        }
    }
    public int calculate_fintess(){
        fitness = Math.abs(35-value);
        return fitness;
    }
    public expression_tree(operation a, int b){
        head = a;
        height = b;
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
            return null;
        }
    }
    static void first_generation(){
        for (int i = 0; i<32; i++){
            expression_tree new_one= new expression_tree(four_operations(), (int)Math.random()*10);
            expression_tree cur = new_one;
            for (int j = 0; j<new_one.height-1; j++){
                expression_tree second = new expression_tree(five_operations(), (int)Math.random()*10);
                cur.left = second;
                cur = second;
                if (cur.head==null){
                    cur.left = new expression_tree((int)Math.random()*10);
                    cur.right = new expression_tree((int)Math.random()*10);
                    break;
                }
            }
            if (new_one.height == 6){
                cur.left = new expression_tree((int)Math.random()*10);
                cur.right = new expression_tree((int)Math.random()*10);
            }
            cur = new_one;
            for (int j = 0; j<new_one.height-1; j++){
                expression_tree second = new expression_tree(five_operations(), (int)Math.random()*10);
                cur.right = second;
                cur = second;
                if (cur.head==null){
                    cur.left = new expression_tree((int)Math.random()*10);
                    cur.right = new expression_tree((int)Math.random()*10);
                    break;
                }
            }
            if (new_one.height == 6){
                cur.left = new expression_tree((int)Math.random()*10);
                cur.right = new expression_tree((int)Math.random()*10);
            }
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
        while (cur.head!=null){
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
        int side = (int)(Math.round(Math.random()));
        if (side ==0){
            cur.left.value = (int)(Math.random()*10);
        }
        else{
            cur.right.value = (int)(Math.random()*10);
        }
        return tree;
    }
    static void sort(){
        for (int i = 0; i<32; i++){
            for (int j = 1; j<32; j++){
                if(generation[j].fitness>generation[j-1].fitness){
                    expression_tree temp = generation[i];
                    generation[i] = generation[j];
                    generation[j] = temp;

                }
            }
        }
    }
    static void selecton(){
        sort();
        for (int i = 16, j = 0; i<32; i++, j++){
            generation[i] = crosover(generation[j],generation[j+1]);
        }
    }

    public static void main(String[] args) {
        first_generation();
        for (int i = 0; i<50; i++){
            fitness();
            sort();
            selecton();
        }
        sort();
        System.out.println(generation[0]);
    }
}
