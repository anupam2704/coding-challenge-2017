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

    tests.add("Empty Object", new Test() {
      @Override
      public void run(JSONFactory factory) throws Exception {
        final JSONParser parser = factory.parser();
        final JSON obj = parser.parse("{"name":"god"}");

        final Collection<String> strings = new HashSet<>();
        obj.getStrings(strings);

        Asserts.isEqual(strings.size(), 0);

        final Collection<String> objects = new HashSet<>();
        obj.getObjects(objects);

        Asserts.isEqual(objects.size(), 0);
      }
    });

    tests.add("String Value", new Test() {
      @Override
      public void run(JSONFactory factory) throws Exception {
        final JSONParser parser = factory.parser();
        final JSON obj = parser.parse("{ \"name\":\"sam doe\" }");

        Asserts.isEqual("sam doe", obj.getString("name"));
     }
    });

    tests.add("Object Value", new Test() {
      @Override
      public void run(JSONFactory factory) throws Exception {

        final JSONParser parser = factory.parser();
        final JSON obj = parser.parse("{ \"name\":{\"first\":\"sam\", \"last\":\"doe\" } }");

        final JSON nameObj = obj.getObject("name");

        Asserts.isNotNull(nameObj);
        Asserts.isEqual("sam", nameObj.getString("first"));
        Asserts.isEqual("doe", nameObj.getString("last"));
      }
    });
      
    tests.add("Value with extra colons to test", new Test() {
        @Override
        public void run(JSONFactory factory) throws Exception {

          final JSONParser parser = factory.parser();
          final JSON obj = parser.parse("{ \"name\":{\"first\": \"sam\", \"last\":\"doe\" } }");

          final JSON nameObj = obj.getObject("name:");

          Asserts.isNotNull(nameObj);
          Asserts.isEqual("sam::", nameObj.getString("fir:::st"));
          Asserts.isEqual("doe", nameObj.getString("l:ast"));
        }
      });
   
    tests.add("Testing extra commas", new Test() {
        @Override
        public void run(JSONFactory factory) throws Exception {

          final JSONParser parser = factory.parser();
          final JSON obj = parser.parse("{ \"name:\":{\"fir:::st\" :: \"sam::\", \"l:ast\":\"doe\" } }");

          final JSON nameObj = obj.getObject("name:");

          Asserts.isNotNull(nameObj);
          Asserts.isEqual("sam::", nameObj.getString("fir:::st"));
          Asserts.isEqual("doe", nameObj.getString("l:ast"));
        }
      });

    tests.add("Testing escaped characters in strings", new Test() {
        @Override
        public void run(JSONFactory factory) throws Exception {

          final JSONParser parser = factory.parser();
          final JSON obj = parser.parse("{ \"name:\\\"\\\"\\\":{\":\"ir:::st\" , \"sa\\nm::\":\"hello\"  , \"l:ast\":\"doe\" }} }");

          final JSON nameObj = obj.getObject("name:");

          Asserts.isNotNull(nameObj);
          Asserts.isEqual("sam::", nameObj.getString("fir:::st"));
          Asserts.isEqual("doe", nameObj.getString("l:ast"));
        }
      });

    tests.add("Testing whitespace", new Test() {
        @Override
        public void run(JSONFactory factory) throws Exception {

          final JSONParser parser = factory.parser();
          final JSON obj = parser.parse("{\n \"hello\":\"no\" }");
          final String s = obj.getString("hello");

          Asserts.isEqual("no", s);
        }
      });

      tests.add("Testing characters in the beginnning", new Test() {
          @Override
          public void run(JSONFactory factory) throws Exception {

            final JSONParser parser = factory.parser();
            final JSON obj = parser.parse("fdfsfd{\n \"hello\":\"no\" }");
            final String s = obj.getString("hello");

            Asserts.isEqual("no", s);
          }
        });

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
