using RTFExporter;
using System.Text;
using System.Xml;
using System.Xml.Linq;

string xmlDir = @"C:\FILES\Egyetem\4_szemeszter\Projlab\projlab-error521\Fungorium\xml";
string output = @"C:\FILES\Egyetem\4_szemeszter\Projlab\projlab-error521\Fungorium\plans.rtf";

var classFiles = Directory.GetFiles(xmlDir).Where(x => x.Contains("model_1_1"));
List<DocClass> all = classFiles
    .Select(x => XElement.Load(new StreamReader(x, Encoding.UTF8)))
    .Select(x => new DocClass(x.Element("compounddef")!))
    .ToList();


RTFDocument doc = new RTFDocument();
all.ForEach(x => x.Print(doc));
doc.paragraphs.ForEach(p => p.style.indent.firstLine = 0);
File.WriteAllText(output, RTFParser.ToString(doc), CodePagesEncodingProvider.Instance.GetEncoding("iso-8859-2")!);


