// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.intelligent.codeu.codingchallenge;

import java.io.IOException;
import java.util.regex.*;

final class MyJSONParser implements JSONParser {

    private String input="";
    private int position;
    
  @Override
  public JSON parse(String in) throws IOException
  {
      
    input=in.trim();
    System.out.println(input);
      
    position=1;
    
    blankSpace();
      if(input.charAt(0)=='{')
      {
          JSON data=parseRecursive();
          if(position<input.length())
          {
              if(Pattern.compile("\\S").matcher(input.substring(position)).find())
              {
                  throw new IOException("Extra characters found at end");
              }
              else
                  return data;
          }
          else
              return data;
      }
      else
      {
          throw new IOException("Extra character found at beginnning");
      }
      // TODO: implement this
   // return new MyJSON();
  }


    
    @Override
    public JSON parseRecursive() throws IOException
    {
        MyJSON data = new MyJSON();
        blankSpace();
        int numEntries=0;
        while(input.charAt(position!='}'))
        {
            blankSpace();
            if(numEntries>0)
            {
                if(input.charAt(position!=','))
                {
                    throw new IOEXception("no comma in between key value pair"+position);
                }
                else
                    position++;
            }
            
            blankSpace();

	    	String key = getString();
	    //	System.out.println("key: " + key);

	    	blankSpace();

	    	if(input.charAt(position)!=':')
            {
	    		//System.out.println(input.substring(index));
	    		throw new IOException("no colon in between key and value at position " + position);
	    	}
	    	else
            {
	    		position++;
	    	}

	    	skipWhiteSpace();
            
            char beginningValue = input.charAt(position);
            
            if(beginningValue=='"')
            {
	        	String value = getString();
	        	//System.out.println("string value: " + value);
	        	numEntries++;

						//checking for duplicate keys
				if(data.strings.containsKey(key) || data.objects.containsKey(key))
                {
				    throw new IOException("duplicate key at position " + position);
				}
				else
                {
							//I know I can use the setString() method I defined in MyJSON.java, but I like this because it's more explicit
	                   data.strings.put(key,value);
				}
	        }
            
            else if(beginningValue=='{')
            {
	           position++;
	        //	System.out.println(input.substring(index));
	        	numEntries++;
				if(data.strings.containsKey(key) || data.objects.containsKey(key))
                {
				    throw new IOException("duplicate key at position " + position);
				}
				else
                {
	        	  data.objects.put(key, parseRecursive());
				}
	        }

	        skipWhiteSpace();

	      }
          return data;
        }

    
    public void blankSpace()
    {
        char next=input.charAt(position);
        while(Character.isWhiteSpace(next))
        {
            next=input.charAt(++position);
        }
        return null;
    }
    

    @Override
    public String getString() throws IOException
    {
	  int startIndex = position+1;
	  String str="";
	  if(input.charAt(position)=='"')
      {
  	     while(true)
         {
  		    position++;
  		    if(input.charAt(position)=='\\')
            {
  			   char afterSlash = input.charAt(position+1);
  			   if(afterSlash=='\\' || afterSlash=='"' || afterSlash=='n' || afterSlash=='t')
               {
  				  position++;
  			   }
  			   else
               {
  				  throw new IOException("invalid escaped character in string at position " + position);
  			   }
  		    }
  		    else if(input.charAt(position)=='"')
            {
  			   str = input.substring(startIndex,position);
  			   break;
  			}
  		}
	  }
    else 
    {
	 // System.out.println(input.substring(index));
	       throw new IOException("invalid input. please try again. position " + position);
	       throw new IOException("invalid input. please try again. position " + position);
    }
  	position++;
  	return str;
  }
}
