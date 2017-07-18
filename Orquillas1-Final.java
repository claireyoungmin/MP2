package orquillas1.pkg1;

import java.io.File;
import java.util.*;

class Orquillas11 {
public static boolean  UpperB, LowerB;
public static int Lowerbound, Upperbound;
 public static void main(String[] args) {
      
       int i =0, j=0, k = 1, flag = 0, StrLenCond = 0, StrLenAccum = 0, StrLenInit = 0;
       int l =0, a=0, b=1, f = 0, q=0, len = 0, totals;
       int ProcessCount = 0, InitialCount = 0, total, product=0, ConditionCount = 0; 
       String st = "", temp;
       String[] myTokens = new String[500];
       String extra[] = new String[100];     
       String Processes[] = new String[50];
       String[] initials = new String[10];
       String[] initials1 = new String[100];
       String[] initLeft = new String[10];
       String[] initRight= new String[10];
       String[] accum = new String[100];
       String[] cond= new String[100];
       String[] Initializers = new String[10];
       String[] Accumulators = new String[10];
       String[] MyProcesses  = new String[10];
       String[] myStrings = new String[10];
       String[] bounds = new String[10];
     
       String cond1 = "";
       String[] accum1 = new String[10];
     
       try{
            Scanner scanner = new Scanner(new File("mp.in"));
            while(scanner.hasNext()){
                temp = scanner.nextLine();
                st = st + temp.trim();
            }
           
            myTokens = Tokenizer(st, "\t(){};", 1000);
            k = strLength(myTokens);
            j = 1;
            while(j<k){
                switch (j){

                    case 1: 
                       // Initializers:
                        if(myTokens[j].contains("<")|| myTokens[j].contains("!=") || myTokens[j].contains("==") || myTokens[j].contains(">")) 
                        {
                            // No initializer in loop
                            j++;
                            flag++;
                            k++;
                        }   
                        else 
                        {
                            myTokens[j] = myTokens[j].replaceAll("(int)|(float)", "");
                            Initializers[f] = myTokens[j];
                            initials = Tokenizer(myTokens[j], ",", 10);
                            StrLenInit = strLength(initials);
                            while(initials[i] != null){
                                initials1 = Tokenizer(initials[i], "=", 100);
                                len = strLength(initials1);
                                for(int m=0; m<len-1; m++){
                                    initLeft[i] = initials1[m];
                                    initRight[i] = initials1[m+1];
                                }
                                i++;
                            }
                           
                            break;
                        }
                        
                    case 2:
                        //Conditions:
                        if (flag == 1)
                        {       
                            myTokens[j-1] = myTokens[j-1].replaceAll("\\s", "");  
                            cond1 = myTokens[j-1];
                            cond = Tokenizer(myTokens[j-1], "*+-/%<>=", 100 );
                            StrLenCond = strLength(cond);
                            break;
                        }
                        else
                        {
                            myTokens[j] = myTokens[j].replaceAll("\\s", "");  
                            cond1 = myTokens[j];
                            cond = Tokenizer(myTokens[j], "*+-/%<>=", 100 );
                            StrLenCond = strLength(cond);
                            break;
                        }
                        

                    case 3: // Accumulators
                        if (flag == 1)
                        {
                            Accumulators[f] = myTokens[j-1];
                            accum1 = Tokenizer(myTokens[j-1], ",", 10);     // tokenizes into different accumulators
                            accum = Tokenizer(myTokens[j-1], "+-*/%", 100); // tokenizes each of the accumulators
                            StrLenAccum = strLength(accum);
                            break;
                        }
                        else
                        {
                            Accumulators[f] = myTokens[j];
                            accum1 = Tokenizer(myTokens[j], ",", 10);
                            accum = Tokenizer(myTokens[j], "+-*/%", 100);
                            StrLenAccum = strLength(accum);
                            break;
                        }

                    default:
                         if (flag == 1)
                        {
                            MyProcesses[q] = myTokens[j-1];
                            q++; 
                            break;
                        }
                        else
                        {
                             MyProcesses[q] = myTokens[j];
                             q++; 
                            break;
                        }
                }
                j++;
            }
            
            for(i=0; i< StrLenCond; i++){                           // checks for the iterator i.e. if the variable is the condition and accumulator
                for(j=0; j<StrLenAccum; j++){
                    if(cond[i].equals(accum[j])){
                        flag = 1;
                        break;
                    }
                }
                if(flag==1)
                    break;                
            }
        
            myStrings[0] = cond[i];     
            while(k!=4){                // loop stores the value of iterator, condition and accumulator
                if(k ==1){
                    l = checkCheck(initLeft, cond[i], StrLenInit); 
                    if(l >=0)
                        myStrings[k] = initRight[l];    
                }
                if(k == 2){
                    myStrings[k] = cond1;  
                }
                if(k == 3){
                    l = checkCheck(accum, cond[i], StrLenAccum);
                    if(l >=0)
                        myStrings[k] = accum1[l];  
                }
                k++;
            }
            i=0;
            
        
            if(LeftRight(cond1, myStrings[0])){
               bounds =  UpperAndLower(myStrings, 1);
            }
            else{
                bounds = UpperAndLower(myStrings, 2);
            }
           
            total = strLength(MyProcesses);
            StringTokenizer strMyTokenizer = new StringTokenizer (Initializers[f],",");
            InitialCount+=strMyTokenizer.countTokens();                                         // gets number of initializers
            StringTokenizer strMyTokens = new StringTokenizer (Accumulators[f],",");
            ProcessCount+=strMyTokens.countTokens();                                            // gets number of accumulators
            ProcessCount+=ProcessCount(total, ProcessCount,MyProcesses);                        // adds accumulators and processes in the loop
            StringTokenizer stringMyTokens = new StringTokenizer (myStrings[2],"*+-/");        
            ConditionCount+=stringMyTokens.countTokens();                                       // counts how many operations are done in the condition statement

            
            totals = ProcessCount + InitialCount;
            
            String FTN  = CalculateTeeOfN(myStrings,bounds,ConditionCount,InitialCount,ProcessCount);
            System.out.println("T(n): " + FTN);
                
       }  
       
        catch(Exception e){
                System.out.println("File not found!");
        }
    
    }     
 public static int isInteger(String string, int def){           // checks if iterator is a string or a variable
 
     try{
         int f = (Integer.valueOf(string));                     // converts string to integer
         return 1;
     }
     catch (NumberFormatException e){
       
         return def;
     
     }
 }
 public static String[] Tokenizer(String tokens, String delimiters, int arrSize){       // tokenizes
 
     String newArray[] = new String [arrSize];
     String tmp;
     int i = 0;
     
     StringTokenizer strToken = new StringTokenizer (tokens, delimiters);
     
        while(strToken.hasMoreTokens()){
            tmp = strToken.nextToken();
            newArray[i] = tmp.replaceAll("\\s", "");
            i++;
        
        }
     return newArray;
 }    
  public static int strLength(String arr[]){     // returns length+1 of string 
     
     int len = 0; 
     
   while(arr[len]!=null){
       len++;
   }

   return len; 
 
 }
 
 public static int checkCheck(String var[], String var2, int length){       // checking for equality of strings
    int a, flag = 0;

     for(a = 0; a < length; a++ ){
                if(var[a].equals(var2))
                    break;
          }
        
    return a;

}
 public static int ProcessCount(int total, int ProcessCount, String Processes[]){

     for(int q=0; q < total; q++){

                             StringTokenizer strTok = new StringTokenizer (Processes[q],"+-/*=");
                             ProcessCount+=strTok.countTokens();
                             ProcessCount--;
                             if(Processes[q].contains("--"))
                                 ProcessCount++;
                             
                             if(Processes[q].contains("++"))
                                 ProcessCount++;
                         
     }
        
     
       return ProcessCount;
 }
 public static boolean LeftRight(String token, String var){
        String[] str = new String[5];
        
        str = Tokenizer(token, "<>=!", 5);
        
        if(str[0].contains(var))
            return true;
        else
           return false; 
    }
 public static String[] UpperAndLower(String[] myStrings, int i){
        String[] bounds = new String[5];
        String condLR[] = new String[2];
        String buffer[] = new String[2];
        String condIter[] = new String[10];
        String accum[] = new String[10];
        accum = Tokenizer(myStrings[3], "+-*/=", 10);
        condLR = Tokenizer(myStrings[2], "<>=!", 2);
        String tempUB = null;
        String buff=myStrings[2];
        int k = 0;
        
        if(i ==2){
            buffer[k] = condLR[k+1];
            buffer[k+1] = condLR[k];
            condLR[k] = buffer[k];
            condLR[k+1] = buffer[k+1];
            if(buff.contains(">"))
                myStrings[2] = buff.replace(">", "<");
            else
                myStrings[2] = buff.replace("<", ">");
        }
        
        condIter = Tokenizer(condLR[0], "*+-%/", 10);
        int size = strLength(condIter);
       
        
        if(myStrings[2].contains("<")){
            bounds[0] = myStrings[1];
            tempUB = condLR[1];
        }
        else if(myStrings[2].contains(">")){
            bounds[0] = condLR[1];
            tempUB = myStrings[1];
        }

        if(myStrings[3].contains("++") || myStrings[3].contains("--") || myStrings[3].contains("+=1") || myStrings[3].contains("-=1")){
            if(size>1){
                if(condLR[0].contains("*")){
                    bounds[1] = tempUB +"^(1/" + size + ")";
                }
                else if(condLR[0].contains("+")){
                    bounds[1] = tempUB + "/" + size;
                }
            }
            else
                bounds[1] = tempUB;      
        }
        else if(myStrings[3].contains("+=") || myStrings[3].contains("-=")){
            
            bounds[1] = tempUB + "/" + accum[1];
              bounds[3] = "2";
        }
        else if(myStrings[3].contains("*=") || myStrings[3].contains("/=")){
            bounds[1] = "log(" + accum[1] +")" + tempUB;
            bounds[3] = "3";
        }
        
         LowerB = (isInteger(bounds[0], -12391)!=-12391);
         UpperB = (isInteger(tempUB, -12391)!=-12391);
            
            if(LowerB)
                Lowerbound = Integer.parseInt(bounds[0]);
            if(UpperB)
                Upperbound = Integer.parseInt(tempUB);
        
        
        
       return bounds;
    }
 public static String CalculateTeeOfN(String[] myStrings, String []bounds, int ConditionCount, int InitialCount, int ProcessCount){
     int TimeComplexity, below1,  LB = 0, UB = 0, value = 0;
     String FinalTee = "";
     String [] below = new String[10];
     String Infinite = "Infinite";
     
     // bounds[1] = contains the upperbound
     // bounds[0] = contains the lowerbound
     
     if(UpperB){                        // Upperbound is integer
     
        if (bounds[1].contains("^")){
             below = Tokenizer(bounds[1], "(^)/", 10);
             below1 = Integer.parseInt(below[2]);
             UB = (int) Math.pow((double) Upperbound, (double) (1.00000000/below1)); // gets power
           
        }
        else if(bounds[1].contains("/")){
             below = Tokenizer(bounds[1], "/", 10);
             below1 = Integer.parseInt(below[1]);
             UB = Upperbound / below1;
         }
         else if(bounds[1].contains("log")){
             below = Tokenizer(bounds[1], "()", 10);
             below1 = Integer.parseInt(below[1]);
             UB = (int) (Math.log((double) Upperbound) / Math.log ((double) below1)); // gets the logarithm
         }
     }
         
     
     value = (int) Math.pow((double) Lowerbound, (double) (ConditionCount));    
   
    if(LowerB && UpperB){          // Lower and Upperbound are both integers
         if(myStrings[2].contains("*") && myStrings[2].contains("=") && value > Upperbound){            // case when the cndition has a multiplication operation
                  return Integer.toString(InitialCount+ConditionCount);                                
             }
         if(myStrings[2].contains("*") && !(myStrings[2].contains("=")) && value >=Upperbound){
                  return Integer.toString(InitialCount+ConditionCount);
         }
         
         if(myStrings[2].contains("=") && Lowerbound > Upperbound)
             return Integer.toString(ConditionCount+InitialCount);
     
         else if(!(myStrings[2].contains("=")) && Lowerbound >= Upperbound){
             return Integer.toString(ConditionCount+InitialCount);    
        
        }
       
     
        if(myStrings[2].contains("<")){
                 if(myStrings[3].contains("/")|| myStrings[3].contains("-"))
                     return Infinite;
        }
        else if (myStrings[2].contains(">")){
                 if(myStrings[3].contains("*")|| myStrings[3].contains("+"))
                     return Infinite;
        }
        if(myStrings[2].contains("=")){
                 return Integer.toString(((ProcessCount+ConditionCount)*Upperbound)-(ProcessCount+ConditionCount)*(Lowerbound) + ProcessCount + ConditionCount + ConditionCount + InitialCount);
                 
        }
        else 
                 return Integer.toString(((ProcessCount+ConditionCount)*Upperbound)-(ProcessCount+ConditionCount)*(Lowerbound)  + ConditionCount + InitialCount);
         
      //   }
     
     }
     else if(LowerB && !UpperB){
        if(myStrings[2].contains("<")){
                 if(myStrings[3].contains("/")|| myStrings[3].contains("-"))
                     return Infinite;
             }
             
             else if(myStrings[2].contains(">")){
                 if(myStrings[3].contains("*")|| myStrings[3].contains("+"))
                     return Infinite;
             }
            if(myStrings[2].contains("=")){
                  TimeComplexity =(-(ProcessCount+ConditionCount)*(Lowerbound) + ProcessCount + ConditionCount + ConditionCount + InitialCount);
                 
                 if(TimeComplexity > 0){
                 
                     FinalTee = Integer.toString(TimeComplexity);
                     FinalTee = (ProcessCount + ConditionCount) + bounds[1] + " + " + FinalTee;
                 }
                 else if(TimeComplexity == 0){
                     FinalTee = Integer.toString(TimeComplexity);
                     FinalTee = (ProcessCount + ConditionCount) + bounds[1];
                 
                 }
                 else{
                     FinalTee = Integer.toString(TimeComplexity);
                     FinalTee = (ProcessCount + ConditionCount) + bounds[1] + FinalTee;
                 
                 }    
     
        }
             else {
                  TimeComplexity =(-(ProcessCount+ConditionCount)*(Lowerbound) + ConditionCount + InitialCount);
                  if(TimeComplexity > 0){
                 
                     FinalTee = Integer.toString(TimeComplexity);
                     FinalTee = (ProcessCount + ConditionCount) + bounds[1] + " + " + FinalTee;
                 }
                 else if(TimeComplexity == 0){
                     FinalTee = Integer.toString(TimeComplexity);
                     FinalTee = (ProcessCount + ConditionCount) + bounds[1];
                 
                 }
                 else{
                     FinalTee = Integer.toString(TimeComplexity);
                     FinalTee = (ProcessCount + ConditionCount) + bounds[1] + FinalTee;
                 
                 }    
             
             }

     }
   
     
     else if(!LowerB && UpperB){     
         return Integer.toString(InitialCount+ConditionCount);
     }
    
     else 
     {
         if(myStrings[2].contains("<")){
                 if(myStrings[3].contains("/")|| myStrings[3].contains("-"))
                     return Infinite;
             }
             
             else if(myStrings[2].contains(">")){
                 if(myStrings[3].contains("*")|| myStrings[3].contains("+"))
                     return Infinite;
             }
         
         if(myStrings[2].contains("=")){
             TimeComplexity = InitialCount + ConditionCount + ConditionCount + ProcessCount;
             FinalTee = Integer.toString(TimeComplexity);
             FinalTee = (ProcessCount + ConditionCount) + bounds[1] + "-" + (ProcessCount + ConditionCount) + bounds[0] + " + "+ FinalTee;
         }    
             else{
                 FinalTee = Integer.toString(ConditionCount + ProcessCount);  
                 FinalTee = (ProcessCount + ConditionCount) + bounds[1] + "-" + (ProcessCount + ConditionCount) + bounds[0] + " + "+ FinalTee;
                     
                     
            }
         
         
         }
     
     return FinalTee;
     
     }
 
}
