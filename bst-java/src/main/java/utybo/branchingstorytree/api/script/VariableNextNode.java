/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * This Source Code Form is "Incompatible With Secondary Licenses", as
 * defined by the Mozilla Public License, v. 2.0.
 */
package utybo.branchingstorytree.api.script;

import utybo.branchingstorytree.api.BSTException;
import utybo.branchingstorytree.api.story.BranchingStory;

/**
 * A Next Node Definer that defines the next node based on the value of a
 * variable : said value is the next node.
 * 
 * @author utybo
 *
 */
public class VariableNextNode implements NextNodeDefiner
{
    private final BranchingStory story;
    private final String variable;

    /**
     * Creates a {@link VariableNextNode}
     * 
     * @param story
     *            The story from which to get the variable
     * @param nextNodeDefiner
     *            The variable's name
     */
    public VariableNextNode(final BranchingStory story, final String nextNodeDefiner)
    {
        this.story = story;
        variable = nextNodeDefiner;
    }

    @Override
    public int getNextNode() throws BSTException
    {
        final Integer i = story.getRegistry().getAllInt().get(variable);
        if(i == null)
        {
            throw new BSTException(-1, "Unknown or unset variable : " + i + " (note : it NEEDS to be an integer)");
        }
        else
        {
            return i;
        }
    }

}