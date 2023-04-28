/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi2023.mathproject;

import java.util.ArrayList;

/**
 *
 * @author Kseny
 */
public class DataStorage {
    String[] namesParameters = {"Среднее геометрическое", "Среднее арифметическое", "Стандартное отклонение",
                          "Минимум", "Максимум", "Размах", "Количество элементов", 
                          "Коэффициент вариации", "Дисперсия", "Доверительный интервал, низ",
                          "Доверительный интервал, верх"};
    String[] namesParameters2 = {"Корреляция", "Ковариация"};
    ArrayList<ArrayList<Double>> samples;
    ArrayList<String> names;
    ArrayList<String> names2;
    
    public DataStorage(){        
        samples = new ArrayList<>();
        names = new ArrayList<>();
        names2 = new ArrayList<>();
    }
    
    public String[] getNamesParameters() {
        return namesParameters;
    }

    public String[] getNamesParameters2() {
        return namesParameters2;
    }
    
    public void setSamples(ArrayList<ArrayList<Double>> samples) {
        this.samples = samples;
    }

    public void setNames(ArrayList<String> names) {
        this.names = names;
        fillNames2();
    }
    
    public ArrayList<ArrayList<Double>> getSamples() {
        return samples;
    }

    public ArrayList<String> getNames() {
        return names;
    }

    public ArrayList<String> getNames2() {
        return names2;
    }
    
    private void fillNames2(){
        for (int i = 0; i < names.size(); ++i){
            names2.add(names.get(i%names.size()) + " & " + names.get((i + 1)%names.size()));
        }
    }
    
    public void clearData(){
        samples.clear();
        names.clear();
        names2.clear();
    }
}
