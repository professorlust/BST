/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * This Source Code Form is "Incompatible With Secondary Licenses", as
 * defined by the Mozilla Public License, v. 2.0.
 */
package utybo.branchingstorytree.api;

public class NodeNotFoundException extends BSTException
{
    private static final long serialVersionUID = 1L;
    private int id;

    public NodeNotFoundException(int id, String sourceFile)
    {
        super(-1, (String)null, sourceFile);
        this.id = id;
    }

    public int getId()
    {
        return id;
    }
}
