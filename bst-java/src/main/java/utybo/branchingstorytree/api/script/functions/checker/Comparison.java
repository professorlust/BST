package utybo.branchingstorytree.api.script.functions.checker;

import utybo.branchingstorytree.api.BSTClient;
import utybo.branchingstorytree.api.BSTException;
import utybo.branchingstorytree.api.script.ScriptChecker;
import utybo.branchingstorytree.api.script.VariableRegistry;

public class Comparison implements ScriptChecker
{

    @Override
    public boolean check(String head, String desc, VariableRegistry registry, BSTClient client) throws BSTException
    {
        final String varName = desc.split(",")[0];
        final Integer var = (Integer)registry.get(varName, 0);
        final String compareTo = desc.split(",")[1];
        final Integer var2 = registry.typeOf(compareTo) == Integer.class ? (Integer)registry.get(compareTo, 0) : Integer.parseInt(compareTo);

        switch(head)
        {
        case "greater":
            return var > var2;
        case "greaterequ":
            return var >= var2;
        case "less":
            return var < var2;
        case "lessequ":
            return var <= var2;
        default:
            throw new BSTException(-1, "Internal error");
        }
    }

    @Override
    public String[] getName()
    {
        return new String[]{"greater", "greaterequ", "less", "lessequ"};
    }

}
