using RTFExporter;
using System.Text;
using System.Xml.Linq;

class DocClass(XElement file)
{
    List<DocItem> items => file.Descendants("memberdef").Select(x => new DocItem(x)).ToList();
    string Description => new TextBlock(file.Element("detaileddescription")!).Text;
    string Name => file.Element("compoundname")!.Value.Split("::")[1];
    XElement? inh => file.Element("inheritancegraph");
    bool IsActive => inh?.Descendants("label").Any(x => x.Value == "IActive") ?? false;
    string AncestorID => inh?.Element("node")!.Element("childnode")?.Attribute("refid")!.Value ?? "";
    string Ancestor => AncestorID == "" ? "" : inh?.Elements("node")!.Single(x => x.Attribute("id")!.Value == AncestorID).Element("label")!.Value ?? "";

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
        p = doc.AppendParagraph();
        p.AppendText("•  ");
        t = p.AppendText("Felelősségek");
        t.style.bold = true;
        t.style.fontSize = 12;
        t.style.fontFamily = "Times New Roman";
        p = doc.AppendParagraph();
        p.style.indent.left = .17f;
        t = p.AppendText(Description);
        t.style.fontSize = 12;
        t.style.fontFamily = "Times New Roman";
        if(Ancestor != "")
        {
            p = doc.AppendParagraph();
            p.AppendText("•  ");
            t = p.AppendText("Ősosztályok");
            t.style.bold = true;
            t.style.fontSize = 12;
            t.style.fontFamily = "Times New Roman";
            p = doc.AppendParagraph();
            p.style.indent.left = .17f;
            t = p.AppendText(Ancestor);
            t.style.fontSize = 12;
            t.style.fontFamily = "Times New Roman";
        }
        if (IsActive)
        {
            p = doc.AppendParagraph();
            p.AppendText("•  ");
            t = p.AppendText("Interfészek");
            t.style.bold = true;
            t.style.fontSize = 12;
            t.style.fontFamily = "Times New Roman";
            p = doc.AppendParagraph();
            p.style.indent.left = .17f;
            t = p.AppendText("IActive");
            t.style.fontSize = 12;
            t.style.fontFamily = "Times New Roman";
        }

        if (Attr.Count != 0)
        {
            p = doc.AppendParagraph();
            p.AppendText("•  ");
            t = p.AppendText("Attribútumok");
            t.style.bold = true;
            t.style.fontSize = 12;
            t.style.fontFamily = "Times New Roman";
            Attr.ForEach(x => x.Print(doc));
        }
        if (Func.Count != 0)
        {
            p = doc.AppendParagraph();
            p.AppendText("•  ");
            t = p.AppendText("Metódusok");
            t.style.bold = true;
            t.style.fontSize = 12;
            t.style.fontFamily = "Times New Roman";
            Func.ForEach(x => x.Print(doc));
        }
    }
}
class DocItem(XElement member)
{
    public enum Type { FUNC, ATTR }

    string TypeStr => member.Ancestors("sectiondef").Single().Attribute("kind")!.Value;
    string DefinitionStr => member.Element("definition")!.Value;
    string ArgStr => member.Element("argsstring")!.Value;
    string Description => new TextBlock(member.Element("detaileddescription")!).Text;
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

class TextBlock(XElement desc)
{
    List<string> text = desc.Elements("para").Where(x => x.Element("parameterlist") is null && x.Element("simplesect") is null).Select(x => x.Value).ToList();
    List<string>? parameters = desc.Elements("para")
        .Select(x => x.Element("parameterlist"))
        .FirstOrDefault(x => x is not null)
        ?.Elements("parameteritem")
        .Select(x => $"{x.Element("parameternamelist")!.Value}: {x.Element("parameterdescription")!.Value}").ToList();
    XElement? ret = desc.Elements("para")
        .Select(x => x.Element("simplesect"))
        .FirstOrDefault(x => x is not null);
    public string? retStr => ret is not null ? $"return: {ret.Value}" : null;

    public string Text
    {
        get
        {
            StringBuilder sb = new StringBuilder();
            sb.AppendLine(string.Join("\n", text));
            if (parameters is not null)
            {
                sb.AppendLine(string.Join("\n", parameters));
            }
            if (retStr is not null)
            {
                sb.AppendLine(retStr);
            }
            return sb.ToString().TrimEnd();

        }

    }

}
