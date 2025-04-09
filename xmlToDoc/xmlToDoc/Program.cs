using RTFExporter;
using System.Text;
using System.Xml.Linq;

string xmlDir = @"C:\FILES\Egyetem\4_szemeszter\Projlab\projlab-error521\Fungorium\xml";
string outputFile = @"C:\FILES\Egyetem\4_szemeszter\Projlab\projlab-error521\Fungorium\plans.rtf";

var classFiles = Directory.GetFiles(xmlDir).Where(x => x.Contains("model_1_1"));
List<DocClass> all = classFiles
    .Select(x => XElement.Load(new StreamReader(x, Encoding.UTF8)))
    .Select(x => new DocClass(x.Element("compounddef")!))
    .ToList();


RTFDocument doc = new RTFDocument();
all.Index().ToList().ForEach(x => x.Item.Print(doc,x.Index+1));
doc.paragraphs.ForEach(p => p.style.indent.firstLine = 0);
bool saved = false;
Dictionary<char, char> change = new Dictionary<char, char>
{
    { 'Ő', 'Ö' },
    { 'ő', 'ö' },
    { 'Ű', 'Ü' },
    { 'ű', 'ü' }
};
string output = RTFParser.ToString(doc);
foreach (var item in change)
{
    output = output.Replace(item.Key.ToString(), $"\\u{(uint)item.Key}{item.Value}");
}
do
{
    try
    {
        File.WriteAllText(outputFile, output, CodePagesEncodingProvider.Instance.GetEncoding("iso-8859-2")!);
        saved = true;
    }
    catch (IOException)
    {
        Console.WriteLine("Close Word!");
        Console.ReadKey();
    }
} while (!saved);

