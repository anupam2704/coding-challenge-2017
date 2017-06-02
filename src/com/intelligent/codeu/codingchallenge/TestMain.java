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
import java.util.HashSet;

final class TestMain {

  public static void main(String[] args) {

    final Tester tests = new Tester();

    tests.add("Empty Object", new Test() { //Test to check for empty object
      @Override
      public void run(JSONFactory factory) throws Exception {
        final JSONParser parser = factory.parser();
        final JSON obj = parser.parse("{ }"); //empty object is provided, so it result PASS

        final Collection<String> strings = new HashSet<>();
        obj.getStrings(strings);

        Asserts.isEqual(strings.size(), 0);

        final Collection<String> objects = new HashSet<>();
        obj.getObjects(objects);

        Asserts.isEqual(objects.size(), 0);
      }
    });

    tests.add("String Value", new Test() { //Test to check for corresponding string value
      @Override
      public void run(JSONFactory factory) throws Exception {
        final JSONParser parser = factory.parser();
        final JSON obj = parser.parse("{ \"name\":\"sam doe\" }"); 

        Asserts.isEqual("sam doe", obj.getString("name")); //it will result PASS
     }
    });

    tests.add("Object Value", new Test() { //Test to check for value of the object
      @Override
      public void run(JSONFactory factory) throws Exception {

        final JSONParser parser = factory.parser();
        final JSON obj = parser.parse("{ \"name\":{\"first\":\"sam\", \"last\":\"doe\" } }");

        final JSON nameObj = obj.getObject("name");

        Asserts.isNotNull(nameObj);
        Asserts.isEqual("sam", nameObj.getString("first")); //it will result PASS
        Asserts.isEqual("doe", nameObj.getString("last"));
      }
    });
      
    tests.add("Missing Colon Test", new Test() { //This test will point out missing colon in the syntax
        @Override
        public void run(JSONFactory factory) throws Exception {

          final JSONParser parser = factory.parser();
          final JSON obj = parser.parse("{ \"name\":{\"first\"\"sam\", \"last\":\"doe\" } }"); //json with colon missing

        final JSON nameObj = obj.getObject("name");

        Asserts.isNotNull(nameObj);
        Asserts.isEqual("sam", nameObj.getString("first"));
        Asserts.isEqual("doe", nameObj.getString("last"));  //it will result FAIL
        }
      });
   
    tests.add("Testing Missing Comma", new Test() { //This test will point out missing comma in the syntax
        @Override
        public void run(JSONFactory factory) throws Exception {

                    final JSONParser parser = factory.parser();
          final JSON obj = parser.parse("{ \"name\":{\"first\":\"sam\" \"last\":\"doe\" } }"); //json without comma

        final JSON nameObj = obj.getObject("name");

        Asserts.isNotNull(nameObj);
        Asserts.isEqual("sam", nameObj.getString("first"));
        Asserts.isEqual("doe", nameObj.getString("last"));      //it will result FAIL
        }
      });

    tests.add("Testing Escaped Characters in Strings", new Test() { //This test will verify the syntax for presence of illegal escape characters
        @Override
        public void run(JSONFactory factory) throws Exception {

          final JSONParser parser = factory.parser();
           final JSON obj = parser.parse("{ \\\"escaped\":\\\\\"characters\"}"); //illegeal escape characters
            final String string = obj.getString("escaped");
              

            Asserts.isEqual("characters",string); //it will result FAIL
          
        }
      });


      tests.add("Testing Characters If Present At Start", new Test() { //This test will check for characters present at the beginning
          @Override
          public void run(JSONFactory factory) throws Exception {

            final JSONParser parser = factory.parser();
            final JSON obj = parser.parse("extrachar{ \"testing\":\"characters\" }"); //extra characters present at the bginning
            final String string = obj.getString("testing");
              

            Asserts.isEqual("characters",string); //this will result FAIL
          }
        });
      
      //Asserts.isEqual() will assert that two parameters in its function are equal and will return values accordingly(boolenan value)

    tests.run(new JSONFactory(){
      @Override
      public JSONParser parser() {
        return new MyJSONParser();
      }

      @Override
      public JSON object() {
        return new MyJSON();
      }
    });
  }
}
