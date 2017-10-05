package SentimentAnalysis;
import java.util.*;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;


public class FeatureExtraction {
    /**
     * @param filePath
     */
    public static void readTxtFile(String filePath,String result){
        try {
                String encoding="UTF-8";
                String resultnumber="";
                File file=new File(filePath);
                File resultfile=new File(result);
                if(file.isFile() && file.exists()){ //判断文件是否存在
                    InputStreamReader read = new InputStreamReader(
                    new FileInputStream(file),encoding);//考虑到编码格式
                    BufferedReader bufferedReader = new BufferedReader(read);
                    String lineTxt = null;
                    Features twitterfeature= new Features();
                    try(FileOutputStream fop=new FileOutputStream(resultfile)){
                    if(!resultfile.exists())
                    {
                    resultfile.createNewFile();
                    }
                   
                    
                    byte[] resultBytes;
                    int negation=0;
                    while((lineTxt = bufferedReader.readLine()) != null){
                    	System.out.println(lineTxt);
                    	
                    		int allcaptest=1;
                    		String [] strArray = lineTxt.split("\\s+");
                    		for(int i=0;i<strArray.length;i++)
                    		{
                    			
                    			allcaptest=1;
                    			twitterfeature.numberofword++;
                    			
                    			if(!Elongated(strArray[i]).equals(""))
                    			{
                    				System.out.println(strArray[i]);
                    				strArray[i]=Elongated(strArray[i]);
                    				twitterfeature.elongated++;
                    			}
                    			
                    			for(int j=0;j<strArray[i].length();j++)
                    			{
                    				if(strArray[i].charAt(j)>='A'&&strArray[i].charAt(j)<='Z')
                    				{
                    				
                    				}
                    				else
                    				{
                    					allcaptest=0;
                    					break;
                    				}
                    				
                    				
                    				
                    			
                    			}
                    			if(allcaptest==1)
                        		{
                        			twitterfeature.allcaps++;
                        		}
                    			
                    			if(readDictionary("negation word",strArray[i]))
                    			{
                    				twitterfeature.negation++;
                    				negation=1;
                    			}
                    			
                    			if(readDictionary("negative",strArray[i]))
                    			{
                    				if(negation==1)
                    				{
                    				twitterfeature.positive++;
                    				}
                    				else
                    				{
                    				twitterfeature.negative++;
                    				}
                    			}
                    			if(readDictionary("positive",strArray[i]))
                    			{
                    				if(negation==1)
                    				{
                    				twitterfeature.negative++;
                    				}
                    				else
                    				{
                    				twitterfeature.positive++;
                    				}
                    			}
                    			if(readDictionary("emoticon_po",strArray[i]))
                    			{
                    				twitterfeature.positive++;
                    				twitterfeature.emoticons++;
                    			}
                    			
                    			if(readDictionary("emoticon_ne",strArray[i]))
                    			{
                    				twitterfeature.negative++;
                    				twitterfeature.emoticons++;
                    			}
                    			if(strArray[i].contains("+emoji"))
                    			{
                    				
                    				twitterfeature.positive=twitterfeature.positive+StringtoDouble(strArray[i]);
                    				twitterfeature.emoticons=twitterfeature.emoticons+StringtoDouble(strArray[i]);
                    			}
                    			if(strArray[i].contains("-emoji"))
                    			{
                    				
                    				twitterfeature.negative=twitterfeature.negative+StringtoDouble(strArray[i]);
                    				twitterfeature.emoticons=twitterfeature.emoticons+StringtoDouble(strArray[i]);
                    			}
                    			
                    			
                    			if(strArray[i].equals(",")||strArray[i].equals(".")||strArray[i].equals("?")||strArray[i].equals("!"))
                    			{
                    				twitterfeature.puctuation++;
                    			}
                    			
                    			if(strArray[i].contains("#urls"))//strArray[i].contains("https://");
                    			{
                    				
                    				twitterfeature.numberofURL=twitterfeature.numberofURL+StringtoDouble(strArray[i]);
                    			}
                    			if(strArray[i].equals("<positive>"))
                    			{
                    				twitterfeature.tag_sentiment=1;
                    			}
                    			if(strArray[i].equals("<negative>"))
                    			{
                    				twitterfeature.tag_sentiment=2;
                    			}
                    			
                    			if(strArray[i].equals("<neutral>"))
                    			{
                    				twitterfeature.tag_sentiment=3;
                    			}
                    			
                    			//System.out.println(strArray[i]+" ");
                    		}
                    		////////////////////////////////////////
                    		
                    		
                        	{
                        		
                        		 
                        		 resultnumber=String.valueOf(twitterfeature.numberofword)+" ";
                        		 resultnumber=resultnumber+String.valueOf(twitterfeature.allcaps)+" ";
                        		 resultnumber=resultnumber+String.valueOf(twitterfeature.emoticons)+" ";
                        		 resultnumber=resultnumber+String.valueOf(twitterfeature.elongated)+" ";
                        		 resultnumber=resultnumber+String.valueOf(twitterfeature.positive)+" ";
                        		 resultnumber=resultnumber+String.valueOf(twitterfeature.negative)+" ";
                        		 resultnumber=resultnumber+String.valueOf(twitterfeature.negation)+" ";
                        		 resultnumber=resultnumber+String.valueOf(twitterfeature.puctuation)+" ";
                        		 resultnumber=resultnumber+String.valueOf(twitterfeature.numberofURL)+" ";
                        		 resultnumber=resultnumber+String.valueOf(twitterfeature.tag_sentiment)+" ";
                        		 resultnumber=resultnumber+"\r\n";
                        		 resultBytes=resultnumber.getBytes();
                                 fop.write(resultBytes);
                                 twitterfeature= new Features();
                        		
                        	}
                    		////////////////////////////////////////
                        	
                    		
                    		
                    	
                    	
                        
                    }
                    read.close();
                    fop.flush();
                    fop.close();
                    }
                    catch(IOException e)
                    {
                    	e.printStackTrace();
                    }
                    
                    
        }else{
            System.out.println("cannot find files");
        }
        } catch (Exception e) {
            System.out.println("read error");
            e.printStackTrace();
        }
     
        
        
        
        
        
        
    }
    public static String Elongated (String elong)
    {
    	boolean elongated=false;
    	String result="";
    	String test="";
    	int num_A=65;
    	int num_a=97;
    	char temp=(char)num_A;
    	char temp2=(char)num_a;
    	String replace="";
    	int total=0;
    	int repeat=0;
    	String old_elong=elong;
    	for(int i=0;i<25;i++)
    	{
    		
    		test="";
    		temp=(char)(num_A+i);
    		repeat=0;
    		total=0;
    		replace="";
    		for(int j=0;j<old_elong.length();j++)
    		{
    			test=test+temp;
    			repeat=elong.indexOf(test);

    			if(repeat==-1)
    			{
    			if(total>=3)
    			{
    			test=test.substring(0,j-1);
    			replace=replace+temp+temp;
    			elong=elong.replace(test,replace);
    			//elongated=true;
    			result=elong;
    			}
    			else
    			{
    			//do nothing
    			}
    			}
    			else
    			{
    			total++;
    			if((j==old_elong.length()-1)&&(total>=3))
    			{
    			replace=replace+temp+temp;
        		elong=elong.replace(test,replace);
        		result=elong;
        		//elongated=true;
    			}
    			}
    			
    			
    		}
    	}
    	
    	
    	for(int i=0;i<25;i++)
    	{
    		test="";
    		temp=(char)(num_a+i);
    		repeat=0;
    		total=0;
    		replace="";
    		for(int j=0;j<old_elong.length();j++)
    		{
    			test=test+temp;
    			repeat=elong.indexOf(test);
    			
    			if(repeat==-1)
    			{
    			if(total>=3)
    			{
    			test=test.substring(0,j);
    			replace=replace+temp+temp;
    			
    			elong=elong.replace(test,replace);
    			result=elong;
    			//elongated=true;
    			}
    			else
    			{
    			//do nothing
    			}
    			}
    			else
    			{
    			total++;
    			if((j==old_elong.length()-1)&&(total>=3))
    			{
    			replace=replace+temp+temp;
    			elong=elong.replace(test,replace);
    			result=elong;
    			//elongated=true;
    			}
    			}
    			
    			
    		
    	}
    }
    	
    	return result;
    	
    }
    public static boolean readDictionary(String filePath, String word){
        try {
        	    filePath="F:\\JavaworkspaceforJ2ee\\561project\\dictionaries\\"+filePath+".txt";
                String encoding="UTF-8";
                File file=new File(filePath);
                if(file.isFile() && file.exists()){ 
                    InputStreamReader read = new InputStreamReader(
                    new FileInputStream(file),encoding);
                    BufferedReader bufferedReader = new BufferedReader(read);
                    String lineTxt = null;
                    while((lineTxt = bufferedReader.readLine()) != null){
                        if(word.equalsIgnoreCase(lineTxt))
                        {
                        	return true;
                        }
                    }
                    read.close();
        }else{
            System.out.println("cannot find files");
        }
        } catch (Exception e) {
            System.out.println("read error");
            e.printStackTrace();
        }
        return false;
     
    }
    public static double StringtoDouble(String str)
	{
		int b=str.indexOf(":");
		String c=str.substring(b+1, b+2);
		double d=Double.valueOf(c);
		return d;
	}
    
    public static void main(String argv[]){
    	String filePath;
    	String result;
    	System.out.println("Please input where you put training and test data(i.e E:\\finaltest)");
    	BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    	
    	String str="";
    	 try {
 			str=in.readLine();
 		} catch (IOException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
    	filePath = str+"\\twittertrain.txt";
        result=str+"\\trainresult.txt";
        readTxtFile(filePath,result);
        System.out.println("1 bencarson,2 benie, 3 hillary, 4 jebbush, 5 omalley, 6 rubio, 7 sanders, 8 tedcruz,9 trump");
        System.out.println("1 twitter, 2 youtube");
        System.out.println("If you choose bencarson and twitter, input 11");
        String str1="";
        in = new BufferedReader(new InputStreamReader(System.in));
        try {
			str1=in.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
       
    	
    	
    	if(str1.equals("11"))
    	{
    	str1="bencarson.txt";
    	}
    	if(str1.equals("21"))
    	{
    		str1="bernie2016.txt";	
    	}
    	if(str1.equals("31"))
    	{
    		str1="hillary2016.txt";
    	}
    	if(str1.equals("41"))
    	{
    		str1="jebbush.txt";
    	}
    	if(str1.equals("51"))
    	{
    		str1="omalley2016.txt";
    	}
    	if(str1.equals("61"))
    	{
    		str1="rubio2016.txt";
    	}
    	if(str1.equals("71"))
    	{
    		str1="sanders2016.txt";
    	}
    	if(str1.equals("81"))
    	{
    		str1="ted-cruz2016.txt";
    	}
    	if(str1.equals("91"))
    	{
    		str1="trump2016.txt";
    	}
    	if(str1.equals("12"))
    	{
    		str1="yt_bencarson_cleansed.txt";
    	}
    	if(str1.equals("22"))
    	{
    		str1="yt_bernie_cleansed.txt";
    	}
    	if(str1.equals("32"))
    	{
    		str1="yt_hillary_cleansed.txt";
    	}
    	if(str1.equals("42"))
    	{
    		str1="yt_jeb-bush_cleansed.txt";
    	}
    	if(str1.equals("52"))
    	{
    		str1="yt_omalley_cleansed.txt";
    	}
    	if(str1.equals("62"))
    	{
    		str1="yt_rubio_cleansed.txt";
    	}
    	if(str1.equals("82"))
    	{
    		str1="yt_ted-cruz_cleansed.txt";
    	}
    	if(str1.equals("92"))
    	{
    		str1="yt_trump_cleansed.txt";
    	}
        
        filePath = str+"\\candidates\\"+str1;
        result=str+"\\result.txt";
        readTxtFile(filePath,result);
    	
    	
  
       
        Classification myclassification=new Classification();
        myclassification.readTest(str+"\\result.txt",str+"\\trainresult.txt");
        
        
        
        
    }
     
     
 
}

class Features{
	double numberofword;
	double allcaps;
	double emoticons;
	double elongated;
	double positive;
	double negative;
	double negation;
	double puctuation;
	double numberofURL;
	double tag_sentiment;
	Features()
	{
		 numberofword=0;
		 allcaps=0;
		 emoticons=0;
		 elongated=0;
		 positive=0;
		 negative=0;
		 negation=0;
		 puctuation=0;
		 numberofURL=0;
		 tag_sentiment=0;
	}
	
	
}