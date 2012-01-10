package collection;
class Fruit {
    public final String name;
    public final int size;
    public final int price;
    
    public Fruit(String name, int size, int price) {
        this.name = name;
        this.size = size;
        this.price = price;
    }
}

public class Knapsack {
    public static void main(String[] args) {
        final int MAX = 8;
        final int MIN = 1;
        int[] item = new int[MAX+1]; 
        int[] value = new int[MAX+1];  

        Fruit fruits[] = {
                new Fruit("李子", 4, 4500), 
                new Fruit("蘋果", 5, 5700), 
                new Fruit("橘子", 2, 2250), 
                new Fruit("甜瓜", 6, 6700),
                new Fruit("草莓", 1, 1100), 
                }; 

        for(int i = 0; i < fruits.length; i++) { 
            for(int s = fruits[i].size; s <= MAX; s++) { 
                int p = s - fruits[i].size; 
                int newValue = value[p] + fruits[i].price; 
                if(newValue > value[s]) {// 找到階段最佳解 
                    value[s] = newValue; 
                    item[s] = i; 
                } 
            } 
        } 

        System.out.println("物品\t價格"); 
        for(int i = MAX; i >= MIN; i -= fruits[item[i]].size) { 
            System.out.println(fruits[item[i]].name+ 
                    "\t" + fruits[item[i]].price); 
        } 

        System.out.println("合計\t" + value[MAX]);  
    }
}