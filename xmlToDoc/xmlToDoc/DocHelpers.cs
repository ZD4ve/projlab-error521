using RTFExporter;
using System.Xml.Linq;

class DocClass(XElement file)
{
    List<DocItem> items => file.Descendants("memberdef").Select(x => new DocItem(x)).ToList();
    string Description => new Paragraph(file.Element("detaileddescription")!).Print();
    string Name => file.Element("compoundname")!.Value.Substring(7);
    XElement? inh => file.Element("inheritancegraph");
    bool IsActive => inh?.Descendants("label").Any(x => x.Value == "IActive") ?? false;
    string Ancestor => InheritanceNode.Ancestor(inh, Name);

    List <DocItem> Attr => items.Where(x => x.MemberType == DocItem.Type.ATTR).ToList();
    List<DocItem> Func => items.Where(x => x.MemberType == DocItem.Type.FUNC).ToList();

    public void Print(RTFDocument doc, int id)
    {   
        var p = doc.AppendParagraph();
        p.style.spaceBefore = 240;
        var t = p.AppendText($"8.1.{id} {Name}");
        t.style.bold = true;
        t.style.fontSize = 13;
        t.style.fontFamily = "Arial";

        PrintHeading(doc, "Felelősségek");
        p = doc.AppendParagraph();
        p.style.indent.left = .17f;
        t = p.AppendText(Description);
        t.style.fontSize = 12;
        t.style.fontFamily = "Times New Roman";

        if(Ancestor != "")
        {
            PrintHeading(doc, "Ősosztályok");
            p = doc.AppendParagraph();
            p.style.indent.left = .17f;
            t = p.AppendText(Ancestor);
            t.style.fontSize = 12;
            t.style.fontFamily = "Times New Roman";
        }
        if (IsActive)
        {
            PrintHeading(doc, "Interfészek");
            p = doc.AppendParagraph();
            p.style.indent.left = .17f;
            t = p.AppendText("IActive");
            t.style.fontSize = 12;
            t.style.fontFamily = "Times New Roman";
        }

        if (Attr.Count != 0)
        {
            PrintHeading(doc, "Attribútumok");
            Attr.ForEach(x => x.Print(doc));
        }
        if (Func.Count != 0)
        {
            PrintHeading(doc, "Metódusok");
            Func.ForEach(x => x.Print(doc));
        }
    }
    
    void PrintHeading(RTFDocument doc, string title)
    {
        var p = doc.AppendParagraph();
        p.AppendText("•  ");
        var t = p.AppendText(title);
        t.style = new RTFTextStyle(false, true, 12, "Times New Roman", Color.black);
    }
}
class DocItem(XElement member)
{
    public enum Type { FUNC, ATTR }

    string TypeStr => member.Ancestors("sectiondef").Single().Attribute("kind")!.Value;
    string DefinitionStr => member.Element("definition")!.Value;
    string ArgStr => member.Element("argsstring")!.Value;
    string Description => new Paragraph(member.Element("detaileddescription")!).Print();
    string Visibility => new Dictionary<string, string>() { { "public", "+" }, { "protected", "#" }, { "private", "-" } }[TypeStr.Split('-')[0]];
    string Definition => DefinitionStr.Replace("abstract ","").Replace("static ","");
    string Name => $"{Visibility}{Definition}{ArgStr}";
    public Type MemberType => TypeStr.Contains("func") ? Type.FUNC : Type.ATTR;
    bool IsStatic => TypeStr.Contains("static");
    bool IsAbstract => DefinitionStr.StartsWith("abstract ");

    public void Print(RTFDocument doc)
    {
        var p = doc.AppendParagraph();
        p.style.indent.left = .375f - 0.17f;
        p.AppendText("•  ");
        var t = p.AppendText(Name);
        t.style = new RTFTextStyle(IsAbstract, true, 12, "Times New Roman", Color.black){ underline = IsStatic ? Underline.Basic : Underline.None};

        p = doc.AppendParagraph();
        p.style.indent.left= .375f;
        t = p.AppendText(Description);
        t.style.fontSize = 12;
        t.style.fontFamily = "Times New Roman";
    }
}

struct Paragraph(XElement para)
{
    public string Print()
    {
        if(para.Elements().Count(x=>x.Name!="ref") == 0) return para.Value;
        if(para.Name == "parameterlist")
        {
            return string.Join("\n",
                para
                .Elements("parameteritem")
                .Select(x => $"{x.Element("parameternamelist")!.Value}: {x.Element("parameterdescription")!.Value}")
                );
        }
        if (para.Name == "simplesect")
        {
            return $"return: {para.Value}";
        }
        return string.Join("\n",
            para
            .Elements()
            .Select(x => new Paragraph(x).Print())
            .Where(x => x != "")
            );
    }
}


class InheritanceNode(XElement node)
{
    public int ID => int.Parse(node.Attribute("id")!.Value);
    public string Name => node.Element("label")!.Value;
    public int ParentID => int.Parse(node.Element("childnode")?.Attribute("refid")!.Value ?? "-1");
    InheritanceNode? Parent { get; set; }

    public static List<InheritanceNode> GetNodes(XElement inh)
    {
        var nodes = inh.Elements("node").Select(x => new InheritanceNode(x)).ToList();
        foreach (var node in nodes)
        {
            node.Parent = nodes.SingleOrDefault(x => x.ID == node.ParentID);
        }
        return nodes;
    }
    public static string Ancestor(XElement? inh, string name)
    {
        if (inh is null) return "";
        var nodes = GetNodes(inh);
        var node = nodes.SingleOrDefault(x => x.Name == name);
        if (node is null) return "";
        List<string> names = new List<string>();
        while (node.Parent is not null)
        {
            names.Add(node.Parent.Name);
            node = node.Parent;
        }
        return string.Join(" --> ", names.Where(x => x != "IActive").Reverse());
    }
}