/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * This Source Code Form is "Incompatible With Secondary Licenses", as
 * defined by the Mozilla Public License, v. 2.0.
 */
package utybo.branchingstorytree.bdf;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import utybo.branchingstorytree.api.BSTException;

public class BDFParser
{
    private static final Pattern startOfInt = Pattern.compile("^([\\w_]+)=(-?\\d+)");
    private static final Pattern startOfString = Pattern.compile("^([\\w_]+)=(.+)");

    public static BDFFile parse(BufferedReader br, String fileName) throws IOException, BSTException
    {
        BDFFile file = new BDFFile();

        String line;
        BDFNode node = null;
        int lineNumber = 0;
        int emptyLinesToAdd = 0;
        while((line = br.readLine()) != null)
        {
            lineNumber++;
            if(line.startsWith("#"))
                continue;
            if(line.isEmpty())
            {
                emptyLinesToAdd++;
            }
            Matcher matcher = startOfInt.matcher(line);
            if(matcher.matches())
            {
                String name = matcher.group(1);
                int i = Integer.parseInt(matcher.group(2));
                node = file.addNode(new BDFIntNode(name, i));
                continue;
            }
            matcher = startOfString.matcher(line);
            if(matcher.matches())
            {
                emptyLinesToAdd = 0;
                String name = matcher.group(1);
                String start = matcher.group(2);
                node = file.addNode(new BDFStringNode(name, start));
                continue;
            }
            if(node instanceof BDFStringNode)
            {
                for(; emptyLinesToAdd > 0; emptyLinesToAdd--)
                {
                    ((BDFStringNode)node).append("\n");
                }
                ((BDFStringNode)node).append("\n" + line);
                continue;
            }
            throw new BSTException(-1, "Unknown syntax at line " + lineNumber + " in file " + fileName);
        }

        return file;
    }
}