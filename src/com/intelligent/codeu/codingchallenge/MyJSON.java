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

import java.util.Collection;
import java.util.HashMap;

final class MyJSON implements JSON {
    
    
    //HashMap structure is used here for handling of data.
    HashMap<String,JSON> objects = new HashMap<String,JSON>(); //HashMap for objects
	HashMap<String,String> strings = new HashMap<String,String>(); //HashMap for strings

  @Override
  public JSON getObject(String name) 
  {
      if(objects.containsKey(name))
      {
		return objects.get(name);         //method  to get the values for object
      }
        return null;
  }

  @Override
  public JSON setObject(String name, JSON value) 
  {
       objects.put(name,value);
       return this;                     //method to set the values for object
  }

  @Override
  public String getString(String name) 
  {
      if(strings.containsKey(name))
      {
			return strings.get(name);     //method to get the values for string
      }
        return null;
  }

  @Override
  public JSON setString(String name, String value) 
  {
      strings.put(name, value);
      return this;                   //method to set the values string
  }

  @Override
  public void getObjects(Collection<String> names) 
  {
      names.addAll(objects.keySet());   //method to get all objects
  }

  @Override
  public void getStrings(Collection<String> names)
  {
      names.addAll(strings.keySet());   //method to get all strings
  }
}
