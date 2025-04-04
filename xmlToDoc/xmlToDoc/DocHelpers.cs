using RTFExporter;
using System.Text;
using System.Xml.Linq;

class DocClass(XElement file)
{
    List<DocItem> items = file.Descendants("memberdef").Select(x => new DocItem(x)).ToList();
    public string Description => new TextBlock(file.Element("detaileddescription")!).Text;
    public string Name => file.Element("compoundname")!.Value.Split("::")[1];
    public List<DocItem> Attr => items.Where(x => x.MemberType == DocItem.Type.ATTR).ToList();
    public List<DocItem> Func => items.Where(x => x.MemberType == DocItem.Type.FUNC).ToList();

    public void Print(RTFDocument doc)
    {   var p = doc.AppendParagraph();
        p.style.spaceBefore = 30;
        var t = p.AppendText(Name);
        t.style.bold = true;
        t.style.fontSize = 13;
        t.style.fontFamily = "Arial";
        p = doc.AppendParagraph();
        t = p.AppendText("Felelősségek");
        t.style.bold = true;
        t.style.fontSize = 12;
        t.style.fontFamily = "Times New Roman";
        p = doc.AppendParagraph();
        t = p.AppendText(Description);
        t.style.fontSize = 12;
        t.style.fontFamily = "Times New Roman";

        if (Attr.Count != 0)
        {
            p = doc.AppendParagraph();
            t = p.AppendText("Attribútumok");
            t.style.bold = true;
            t.style.fontSize = 12;
            t.style.fontFamily = "Times New Roman";
            Attr.ForEach(x => x.Print(doc));
        }
        if (Func.Count != 0)
        {
            p = doc.AppendParagraph();
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

    public string TypeStr = member.Ancestors("sectiondef").First().Attribute("kind")!.Value;
    public string Definition = member.Element("definition")!.Value;
    public string ArgStr = member.Element("argsstring")!.Value;
    public string Description = new TextBlock(member.Element("detaileddescription")!).Text;
    public string Visibility => new Dictionary<string, string>() { { "public", "+" }, { "protected", "#" }, { "private", "-" } }[TypeStr.Split('-')[0]];
    public string Name => $"{Visibility} {Definition}{ArgStr}";
    public Type MemberType => TypeStr.Contains("func") ? Type.FUNC : Type.ATTR;
    public bool IsStatic => TypeStr.Contains("static");

    public void Print(RTFDocument doc)
    {
        var p = doc.AppendParagraph();
        p.style.indent = new Indent(0, 0.5f, 0);
        var t = p.AppendText(Name + ":");
        t.style.bold = true;
        t.style.fontSize = 12;
        t.style.fontFamily = "Times New Roman";

        p = doc.AppendParagraph();
        p.style.indent = new Indent(0, 0.75f, 0);
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
        .FirstOrDefault(x => x is not null, null)
        ?.Elements("parameteritem")
        .Select(x => $"{x.Element("parameternamelist")!.Value}: {x.Element("parameterdescription")!.Value}").ToList();
    XElement? ret = desc.Elements("para")
        .Select(x => x.Element("simplesect"))
        .FirstOrDefault(x => x is not null, null);
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
            return sb.ToString();

        }

    }

}
