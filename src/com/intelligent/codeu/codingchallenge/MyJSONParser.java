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

    private String json_input=""; //this is the input data supplied
    private int position; //holds position of the character in string
    
  @Override
  public JSON parse(String in) throws IOException
  {
      
    json_input=in.trim(); //removes leading and trailing spaces
    System.out.println(json_input);
      
    position=1;
    
    blankSpace(); //calling blackspace function
      if(json_input.charAt(0)=='{') //checking if it starts with '{'
      {
          JSON json_data=syntaxParser(); //calling syntax parser functoin
          if(position<json_input.length())
          {
              if(Pattern.compile("\\S").matcher(json_input.substring(position)).find()) //pattern matcher function to match similar pattern in a string
              {
                  return json_data;
              }
              else
                  throw new IOException("Extra characters found at end"); //exception if extra characters are found at end
          }
          else
              return json_data;
      }
      else
      {
          throw new IOException("Extra character found at beginnning"); //exception if extra character found at beginning
      }
  }

    
    public void blankSpace() //this function is to skip blankspace by incrementing posiiton
    {
        char next_char=json_input.charAt(position);
        while(Character.isWhitespace(next_char))
        {
            next_char=json_input.charAt(++position); //next_char refers to character after a blank space
        }
    }

    
    public JSON syntaxParser() throws IOException //function responsible for parsing
    {
        MyJSON json_data = new MyJSON(); //creating object of MyJSON class
        blankSpace();
        int count=0; //initiating count by 0
        while(json_input.charAt(position)!='}') //will run until end i.e till '}' is not found
        {
            blankSpace(); 
            if(count>0) 
            {
                if(json_input.charAt(position)!=',') //if ',' is missing or not found at that position
                {
                    throw new IOException("Comma missing in key value pair"); //exception to handle comma not found case
                }
                else
                    position++;
            }
            
            blankSpace();

	    	String key = getString(); //gets the string

	    	blankSpace(); 

	    	if(json_input.charAt(position)!=':') //if ':' is missing or not found at that position
            {
	    		throw new IOException("Colon missing in key value pair");//exception to handle colon not found case
	    	}
	    	else
            {
	    		position++;
	    	}

	    	blankSpace();
            
            char initial_value = json_input.charAt(position);
            
            if(initial_value=='"') //if the value to begin with is '"'
            {
	        	String value = getString(); //store the string in value
	        	count++; //increment count

				if(json_data.strings.containsKey(key) || json_data.objects.containsKey(key)) //if any of the two coditions is satisfied, or condition is used
                {
				    throw new IOException("Duplicate keys"); //exception for duplicacy
				}
				else
                {
	                   json_data.strings.put(key,value); //putting the values
				}
	        }
            
            else if(initial_value=='{') //if the value to begin with is '{'
            {
	            position++;
	        	count++;
				if(json_data.strings.containsKey(key) || json_data.objects.containsKey(key))
                {
				    throw new IOException("Duplicate keys");
				}
				else
                {
	        	  json_data.objects.put(key, syntaxParser()); //calling the syntaxParser funciton
				}
	        }

	        blankSpace();

	      }
          return json_data; //this function finally returns json data
        }

    
    public String getString() throws IOException //function to get the string
    {
	  int ini_position = position+1; //setting initial position as incrementation of position
	  String string=""; //empty string to hold values
	  if(json_input.charAt(position)=='"') //checking if the character at given position is '"'
      {
  	     while(true) //will run as long as it is true
         {
  		    position++;
  		    if(json_input.charAt(position)=='\\') //condition for characters
            {
  			   char escape = json_input.charAt(position+1); //getting characters into escape variable
  			   if(escape=='t' || escape=='n' || escape=='"' || escape=='\\') //escape sequence cases
               {
  				  position++;
  			   }
  			   else
               {
  				  throw new IOException("Contains invalid escape character"); //exception if invalid escape character is found
  			   }
  		    }
  		    else if(json_input.charAt(position)=='"') //condition for characters
            {
  			   string = json_input.substring(ini_position,position); //getting a substirng from that string by starting position and current position
  			   break; //exit from here
  			}
  		}
	  }
      else 
      {
	       throw new IOException("Invalid escaped characacter found"); //exception if an invalid escape sequence/character is encountered
      }
  	  position++;
  	  return string; //returns the string
  }
}
