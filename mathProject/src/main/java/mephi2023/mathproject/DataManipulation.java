/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi2023.mathproject;

import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.table.DefaultTableModel;
import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.stat.correlation.Covariance;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

/**
 *
 * @author Kseny
 */
public class DataManipulation {
    private static DescriptiveStatistics doDescriptiveStatistics(ArrayList<Double> s){
        DescriptiveStatistics descriptiveStatistics = new DescriptiveStatistics();
        for (double v : s) {
            descriptiveStatistics.addValue(v);
        }
        return descriptiveStatistics;
    }
    
    public static ArrayList<ArrayList<Object>> countParams(ArrayList<ArrayList<Double>> samples){
        ArrayList<ArrayList<Object>> parameters = new ArrayList<>();
        int max_length = -1;
        for (int i = 0; i < samples.size(); ++i){
            if (samples.get(i).size()>max_length){
                max_length = samples.get(i).size();
            }
        }
        for (int i = 0; i < samples.size(); ++i){
            while(samples.get(i).size() < max_length){
                samples.get(i).add(0.0);
            }
            System.out.println(samples.get(i).size());
        }
        for (int i = 0; i < samples.size(); ++i){
            ArrayList<Object> sampleParameters = new ArrayList<>();
            ArrayList<Double> s = samples.get(i);
            DescriptiveStatistics ds = doDescriptiveStatistics(s);
            double count = 100000;
            //1.	Рассчитать среднее геометрическое для каждой выборки 
            double meanG = ds.getGeometricMean();
            if (meanG > 0){
                sampleParameters.add(Math.round(meanG*count)/count);
            } else {
                sampleParameters.add(meanG);
            }
            
            //2.	Рассчитать среднее арифметическое для каждой выборки 
            //double meanA = ds.getMean();
            sampleParameters.add(Math.round(ds.getMean()*count)/count);
            //3.	Рассчитать оценку стандартного отклонения для каждой выборки
            double standardDeviation = ds.getStandardDeviation();
            sampleParameters.add(Math.round(standardDeviation*count)/count);
            //10.	Рассчитать максимумы и минимумы для каждой выборки
            double minElem = ds.getMin();
            double maxElem = ds.getMax();
            sampleParameters.add(Math.round(minElem*count)/count);
            sampleParameters.add(Math.round(maxElem*count)/count);
            //4.	Рассчитать размах каждой выборки                
            //double range = maxElem - minElem;            
            sampleParameters.add(Math.round((maxElem - minElem)*count)/count);
            //6.	Рассчитать количество элементов в каждой выборке
            //double countElem = ds.getN();       
            sampleParameters.add(ds.getN());
            //7.	Рассчитать коэффициент вариации для каждой выборки
            //double coefVar = ds.getVariance();
            sampleParameters.add(Math.round(ds.getVariance()*count)/count);
            //8.	Рассчитать для каждой выборки построить доверительный интервал для мат. ожидания (Случайные числа подчиняются нормальному закону распределения)
            //double lowerBond = ds.getPercentile(5);
            //double highBond = ds.getPercentile(95);
            sampleParameters.add(Math.round(ds.getPercentile(5)*count)/count);
            sampleParameters.add(Math.round(ds.getPercentile(95)*count)/count);
            //9.	Рассчитать оценку дисперсии для каждой выборки 
            //double dispersion = standardDeviation*standardDeviation;
            sampleParameters.add(Math.round(standardDeviation*standardDeviation*count)/count);
            //5.	Рассчитать коэффициенты ковариации для всех пар случайных чисел
            PearsonsCorrelation ps = new PearsonsCorrelation();                
            //double corr = ps.correlation(s.stream().mapToDouble(d -> d).toArray(), 
            //                             s.stream().mapToDouble(d -> d).toArray());
            try {
                int next_i = (i+1)%samples.size();
                sampleParameters.add(Math.round(ps.correlation(s.stream().mapToDouble(d -> d).toArray(),
                        samples.get(next_i).stream().mapToDouble(d -> d).toArray())*count)/count);

                //double corr2 = ps.correlation(s.stream().mapToDouble(d -> d).toArray(),
                //        samples.get(next_i).stream().mapToDouble(d -> d).toArray());
                Covariance c = new Covariance();
                sampleParameters.add(Math.round(c.covariance(s.stream().mapToDouble(d -> d).toArray(),
                        samples.get(next_i).stream().mapToDouble(d -> d).toArray())*count)/count);
            } catch (MathIllegalArgumentException ex) {
                sampleParameters.add(null);
                sampleParameters.add(null);
            }
            parameters.add(sampleParameters);
        }
        
        return parameters;        
    }
    
    public static DefaultTableModel drawModel(ArrayList<ArrayList<Object>> parameters, 
            ArrayList<String> names, String[] namesParameters, int shift){
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Parameters");
        for (int i = 0; i < names.size(); ++i){
            model.addColumn(names.get(i));
        }
        
        for (int i = 0; i < namesParameters.length; i++) {
            Object[] values = new Object[names.size()+1];
            values[0] = namesParameters[i];
            for (int j = 0; j < names.size(); j++){
                values[j+1] = parameters.get(j).get(i + shift);
                System.out.print(values[j+1] + " ");
            }
            System.out.println("*");
            model.addRow(values);
        }
        return model;
    }

    
}
