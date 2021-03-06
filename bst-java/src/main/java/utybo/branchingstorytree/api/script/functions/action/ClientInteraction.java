/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * This Source Code Form is "Incompatible With Secondary Licenses", as
 * defined by the Mozilla Public License, v. 2.0.
 */
package utybo.branchingstorytree.api.script.functions.action;

import utybo.branchingstorytree.api.BSTClient;
import utybo.branchingstorytree.api.BSTException;
import utybo.branchingstorytree.api.script.ScriptAction;
import utybo.branchingstorytree.api.story.BranchingStory;

/**
 * Implementation of multiple actions related to interaction with
 * {@link BSTClient} objects : input and exit.
 *
 * @author utybo
 *
 */
public class ClientInteraction implements ScriptAction
{

    @Override
    public void exec(final String head, final String desc, final int line,
            final BranchingStory story, final BSTClient client) throws BSTException
    {
        switch(head)
        {
        case "input":
            final String varName = desc.split(",")[0];
            final String msg = desc.substring(desc.indexOf(',') + 1);
            story.getRegistry().put(varName, client.askInput(msg));
            break;
        case "exit":
            client.exit();
            break;
        default:
            // Can't happen
            break;
        }
    }

    @Override
    public String[] getName()
    {
        return new String[] {"input", "exit"};
    }

}
