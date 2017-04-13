/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * This Source Code Form is "Incompatible With Secondary Licenses", as
 * defined by the Mozilla Public License, v. 2.0.
 */
package utybo.branchingstorytree.swing.impl;

import java.io.File;
import java.io.FileNotFoundException;

import org.apache.commons.io.FilenameUtils;

import utybo.branchingstorytree.api.BSTClient;
import utybo.branchingstorytree.api.BSTException;
import utybo.branchingstorytree.api.story.BranchingStory;
import utybo.branchingstorytree.brm.BRMResourceConsumer;

public class BRMFileClient implements BRMRestorableHandler
{
    private final File bstFileLocation;
    private final BSTClient client;
    private final BranchingStory origin;
    private boolean initialized = false;

    public BRMFileClient(final File bstFile, final BSTClient client, BranchingStory origin)
    {
        bstFileLocation = bstFile;
        this.client = client;
        this.origin = origin;
    }

    @Override
    public void loadAuto() throws BSTException
    {
        initialized = true;
        origin.getRegistry().put("__brm_initialized", 1);
        final File parent = bstFileLocation.getParentFile();
        final File resources = new File(parent, "resources");
        if(resources.exists() && resources.isDirectory())
        {
            // Analysis of module directories list
            for(final File moduleFolder : resources.listFiles())
            {
                // Analysis of module directory
                if(!moduleFolder.isDirectory())
                {
                    continue;
                }
                final String module = moduleFolder.getName();
                final BRMResourceConsumer handler = client.getResourceHandler(module);
                if(handler != null)
                {
                    for(final File file : moduleFolder.listFiles())
                    {
                        try
                        {
                            handler.load(file, FilenameUtils.getBaseName(file.getName()));
                        }
                        catch(FileNotFoundException e)
                        {
                            throw new Error("Impossible state", e);
                        }
                    }
                }
            }
        }
    }

    public void restoreSaveState() throws BSTException
    {
        Object o = origin.getRegistry().get("__brm_initialized", 0);
        if(!initialized && o instanceof Integer && (Integer)o == 1)
        {
            loadAuto();
        }
    }
}