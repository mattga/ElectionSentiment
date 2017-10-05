package SentimentAnalysis;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class Classification {
	Classification()
	{
		
	}
	
	public int[] readTrain(String filePath, FeaturesforBayes testFeature){
		        int[] finalresult=new int[2];
		        int kforKNN=5;
		        double[] candidateforKNN=new double[kforKNN];
		        double[] candidatesentiment=new double[kforKNN];
		        FeaturesforBayes[] TrainFeature=new FeaturesforBayes[4];
		        FeaturesforBayes[] testresult=new FeaturesforBayes[4];
		        for(int i=0;i<4;i++)
		        {
		        	TrainFeature[i]=new FeaturesforBayes();
		        	testresult[i]=new FeaturesforBayes();
		        }
		        
		        for(int i=0;i<kforKNN;i++)
		        {
		        	
		        	
		        	candidateforKNN[i]=-1;
		        	
		        }
		        for(int i=0;i<kforKNN;i++)
         		        {
         		        	
         		        	
         		        	candidatesentiment[i]=0;
         		        	
         		        }
		       
		        
		        double[] numofsent=new double[4];
		        for(int i=0;i<4;i++)
		        {
		        	numofsent[i]=0;
		        }
		        
		        int numoftotal=0;
		        double[] probability=new double[3];
		        int[] numsentiment=new int[3];
        try {
                String encoding="UTF-8";
                File file=new File(filePath);
                if(file.isFile() && file.exists()){ 
                    InputStreamReader read = new InputStreamReader(
                    new FileInputStream(file),encoding);
                    BufferedReader bufferedReader = new BufferedReader(read);
                    String lineTxt = null;
                    while((lineTxt = bufferedReader.readLine()) != null){
                    	
                    	 
                    	
                    	
                    	
                    	String [] strArray = lineTxt.split("\\s+");
                    	
                    	//for(int i=1;i<3;i++)
                    	int i=0;
                    	if(Double.valueOf(strArray[9])!=0)
                    	{
                    		double a=Double.valueOf(strArray[9]);
                    		i=(int)a;
                    	}
                    	{
                    	
                    	
                    	TrainFeature[i].numberofword=Double.valueOf(strArray[0]);
                        TrainFeature[i].allcaps=Double.valueOf(strArray[1]);
                        TrainFeature[i].emoticons=Double.valueOf(strArray[2]);
                        TrainFeature[i].elongated=Double.valueOf(strArray[3]);
                        TrainFeature[i].positive=Double.valueOf(strArray[4]);
                        TrainFeature[i].negative=Double.valueOf(strArray[5]);
                        TrainFeature[i].negation=Double.valueOf(strArray[6]);
                        TrainFeature[i].puctuation=Double.valueOf(strArray[7]);
                        TrainFeature[i].numberofURL=Double.valueOf(strArray[8]);
                        TrainFeature[i].tag_sentiment=Double.valueOf(strArray[9]);
                        //////////////////////KNN///////////////////////////////
                        double temp=testFeature.numberofword*TrainFeature[i].numberofword+testFeature.allcaps*TrainFeature[i].allcaps;
                        temp=temp+testFeature.emoticons*TrainFeature[i].emoticons+testFeature.elongated*TrainFeature[i].elongated;
                        temp=temp+testFeature.positive*TrainFeature[i].positive+testFeature.negative*TrainFeature[i].negative;
                        temp=temp+testFeature.negation*TrainFeature[i].negation+testFeature.puctuation*TrainFeature[i].puctuation;
                        temp=temp+testFeature.numberofURL*TrainFeature[i].numberofURL;
                        
                        double temp1=TrainFeature[i].numberofword*TrainFeature[i].numberofword+TrainFeature[i].allcaps*TrainFeature[i].allcaps;
                        temp1=temp1+TrainFeature[i].emoticons*TrainFeature[i].emoticons+TrainFeature[i].elongated*TrainFeature[i].elongated;
                        temp1=temp1+TrainFeature[i].positive*TrainFeature[i].positive+TrainFeature[i].negative*TrainFeature[i].negative;
                        temp1=temp1+TrainFeature[i].negation*TrainFeature[i].negation+TrainFeature[i].puctuation*TrainFeature[i].puctuation;
                        temp1=temp1+TrainFeature[i].numberofURL*TrainFeature[i].numberofURL;
                        temp1=Math.sqrt(temp1);
                        
                        double temp2=testFeature.numberofword*testFeature.numberofword+testFeature.allcaps*testFeature.allcaps;
                        temp2=temp2+testFeature.emoticons*testFeature.emoticons+testFeature.elongated*testFeature.elongated;
                        temp2=temp2+testFeature.positive*testFeature.positive+testFeature.negative*testFeature.negative;
                        temp2=temp2+testFeature.negation*testFeature.negation+testFeature.puctuation*testFeature.puctuation;
                        temp2=temp2+testFeature.numberofURL*testFeature.numberofURL;
                        temp2=Math.sqrt(temp2);
                        
                        double cosine=temp/(temp1*temp2);
                        if(numoftotal<kforKNN)
                        {
                        candidateforKNN[numoftotal]=cosine;
                        candidatesentiment[numoftotal]=TrainFeature[i].tag_sentiment;
                        
                        }
                        else
                        {
                        	
                        	for(int l=0;l<kforKNN;l++)
                        	{
                        		if(cosine>candidateforKNN[l]&&TrainFeature[i].tag_sentiment!=0)
                        		{
                        			candidateforKNN[l]=cosine;
                        			candidatesentiment[l]=TrainFeature[i].tag_sentiment;
                        			
                        		}
                        		
                        	}
                        }
                        numoftotal++;
                        
                        ///////////////////////Naive Bayes//////////////////////////////
                        if((Math.abs(testFeature.numberofword-TrainFeature[i].numberofword)==0&&testFeature.numberofword!=0&&TrainFeature[i].numberofword!=0)||(testFeature.numberofword==0&&TrainFeature[i].numberofword==0))
                        {
                        	testresult[i].numberofword++;
                        }
                        
                        if((Math.abs(testFeature.allcaps-TrainFeature[i].allcaps)==0&&testFeature.allcaps!=0&&TrainFeature[i].allcaps!=0)||(testFeature.allcaps==0&&TrainFeature[i].allcaps==0))
                        {
                        	testresult[i].allcaps++;
                        }
                        
                        if((Math.abs(testFeature.emoticons-TrainFeature[i].emoticons)<=1&&testFeature.emoticons!=0&&TrainFeature[i].emoticons!=0)||(testFeature.emoticons==0&&TrainFeature[i].emoticons==0))
                        {
                        	testresult[i].emoticons++;
                        }
                        
                        if((Math.abs(testFeature.elongated-TrainFeature[i].elongated)==0&&testFeature.elongated!=0&&TrainFeature[i].elongated!=0)||(testFeature.elongated==0&&TrainFeature[i].elongated==0))
                        {
                        	testresult[i].elongated++;
                        }
                        
                        if((Math.abs(testFeature.positive-TrainFeature[i].positive)<=3&&testFeature.positive!=0&&TrainFeature[i].positive!=0)||(testFeature.positive==0&&TrainFeature[i].positive==0))
                        {
                        	testresult[i].positive++;
                        }
                        
                        if((Math.abs(testFeature.negative-TrainFeature[i].negative)<=3&&testFeature.negative!=0&&TrainFeature[i].negative!=0)||(testFeature.negative==0&&TrainFeature[i].negative==0))
                        {
                        	testresult[i].negative++;
                        }
                        
                        if((Math.abs(testFeature.negation-TrainFeature[i].negation)==0&&testFeature.negation!=0&&TrainFeature[i].negation!=0)||(testFeature.negation==0&&TrainFeature[i].negation==0))
                        {
                        	testresult[i].negation++;
                        }
                        
                        if((Math.abs(testFeature.puctuation-TrainFeature[i].puctuation)==0&&testFeature.puctuation!=0&&TrainFeature[i].puctuation!=0)||(testFeature.puctuation==0&&TrainFeature[i].puctuation==0))
                        {
                        	testresult[i].puctuation++;
                        }
                        
                        if((Math.abs(testFeature.numberofURL-TrainFeature[i].numberofURL)==0&&testFeature.numberofURL!=0&&TrainFeature[i].numberofURL!=0)||(testFeature.numberofURL==0&&TrainFeature[i].numberofURL==0))
                        {
                        	testresult[i].numberofURL++;
                        }
                        
                        
                        
                        	numofsent[i]++;
                        
                      
                    	
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
    
        for(int i=1;i<4;i++)
        {
        	if(testresult[i].numberofword==0)
        	{testresult[i].numberofword=1;}
        	if(testresult[i].allcaps==0)
            {testresult[i].allcaps=1;}
        	if(testresult[i].emoticons==0)
            {testresult[i].emoticons=1;}
        	if(testresult[i].elongated==0)
            {testresult[i].elongated=1;}
        	if(testresult[i].positive==0)
            {testresult[i].positive=1;}
        	if(testresult[i].negative==0)
            {testresult[i].negative=1;}
        	if(testresult[i].negation==0)
            {testresult[i].negation=1;}
        	if(testresult[i].puctuation==0)
            {testresult[i].puctuation=1;}
        	if(testresult[i].numberofURL==0)
            {testresult[i].numberofURL=1;}
        }
        
        double sum=0;
        for(int i=0;i<4;i++)
        {
        	sum=sum+numofsent[i];
        }
        
        for(int i=1;i<4;i++)
        {
        	testresult[i].numberofword=testresult[i].numberofword*sum/numofsent[i];
            testresult[i].allcaps=testresult[i].allcaps*sum/numofsent[i];
            testresult[i].emoticons=testresult[i].emoticons*sum/numofsent[i];
            testresult[i].elongated=testresult[i].elongated*sum/numofsent[i];
            testresult[i].positive=testresult[i].positive*sum/numofsent[i];
            testresult[i].negative=testresult[i].negative*sum/numofsent[i];
            testresult[i].negation=testresult[i].negation*sum/numofsent[i];
            testresult[i].puctuation=testresult[i].puctuation*sum/numofsent[i];
            testresult[i].numberofURL=testresult[i].numberofURL*sum/numofsent[i];
            
            probability[i-1]=testresult[i].numberofword*testresult[i].allcaps*testresult[i].emoticons*testresult[i].elongated;
            probability[i-1]=probability[i-1]*testresult[i].positive*testresult[i].negative*testresult[i].negation;
            probability[i-1]=probability[i-1]*testresult[i].puctuation*testresult[i].numberofURL*numofsent[i]/sum;
            
        
        }
               
     
        
        finalresult[0]=0;
        double max=0;
        for(int i=0;i<3;i++)
        {
        	if(max<probability[i])
        	{
        		max=probability[i];
        		finalresult[0]=i+1;
        	}
        }
        
      //////////////////////////////////////////////////////////////////////
        numsentiment[0]=0;
        numsentiment[1]=0;
      
        
        
        for(int l=0;l<kforKNN;l++)
    	{
    			
        	for(int i=1;i<4;i++)
        	{
        		if(candidatesentiment[l]==i)
        		{
        			numsentiment[i-1]++;
        		}
        	}
        	
    		
    	}
        max=-1;
        for(int i=0;i<3;i++)
        {
        	if(max<numsentiment[i])
        	{
        		max=numsentiment[i];
        		finalresult[1]=i+1;
        	}
        }
        
      ///////////////////////////////////////////////////
        
     return finalresult;
    }
	
	public void readTest(String filePathtest,String filePathtrain){
		FeaturesforBayes testFeature=new FeaturesforBayes();
		double totalnum=0;
		double[] posnum=new double[2];
		double[] negnum=new double[2];
		for(int i=0;i<2;i++)
		{
			posnum[i]=0;
			negnum[i]=0;
		}
        try {
        	    //filePath="E:\\twittertest.txt";
                String encoding="UTF-8";
                File file=new File(filePathtest);
                if(file.isFile() && file.exists()){ 
                    InputStreamReader read = new InputStreamReader(
                    new FileInputStream(file),encoding);
                    BufferedReader bufferedReader = new BufferedReader(read);
                    String lineTxt = null;
                    while((lineTxt = bufferedReader.readLine()) != null){
                    	String [] strArray = lineTxt.split("\\s+");
                    	totalnum++;
                    	testFeature.numberofword=Double.valueOf(strArray[0]);
                    	testFeature.allcaps=Double.valueOf(strArray[1]);
                    	testFeature.emoticons=Double.valueOf(strArray[2]);
                    	testFeature.elongated=Double.valueOf(strArray[3]);
                    	testFeature.positive=Double.valueOf(strArray[4]);
                    	testFeature.negative=Double.valueOf(strArray[5]);
                    	testFeature.negation=Double.valueOf(strArray[6]);
                    	testFeature.puctuation=Double.valueOf(strArray[7]);
                    	testFeature.numberofURL=Double.valueOf(strArray[8]);
                    	testFeature.tag_sentiment=Double.valueOf(strArray[9]);
                    	int[] sentiment=new int[2];
                    	sentiment=readTrain(filePathtrain, testFeature);
                    	
                    	String[] aaa=new String[3];
                    	
                    	if(sentiment[0]==1)
                    	{
                    		aaa[0]="positive";
                    	}
                    	if(sentiment[0]==2)
                    	{
                    		aaa[0]="negative";
                    	}
                    	if(sentiment[0]==3)
                    	{
                    		aaa[0]="neutral";
                    	}
                    	
                    	if(sentiment[1]==1)
                    	{
                    		aaa[1]="positive";
                    	}
                    	if(sentiment[1]==2)
                    	{
                    		aaa[1]="negative";
                    	}
                    	if(sentiment[1]==3)
                    	{
                    		aaa[1]="neutral";
                    	}
                    	
                    	System.out.println(totalnum+" "+aaa[0]+" "+aaa[1]);
                    	
                    	for(int i=0;i<2;i++)
                    	{
                    	if(sentiment[i]==1)
                    	{
                    		posnum[i]++;
                    	}
                    	if(sentiment[i]==2)
                    	{
                    		negnum[i]++;
                    	}
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
        System.out.println("Naive Bayes:");
        System.out.println("positive= "+posnum[0]/totalnum*100+"%");
        System.out.println("negative= "+negnum[0]/totalnum*100+"%");
        System.out.println("neutral= "+(totalnum-posnum[0]-negnum[0])/totalnum*100+"%");
        System.out.println("KNN:");
        System.out.println("positive= "+posnum[1]/totalnum*100+"%");
        System.out.println("negative= "+negnum[1]/totalnum*100+"%");
        System.out.println("neutral= "+(totalnum-posnum[1]-negnum[1])/totalnum*100+"%");
        System.out.println("Total number: "+totalnum);
        
       
     
    }
	
	
	
}

class FeaturesforBayes{
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
	FeaturesforBayes()
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
