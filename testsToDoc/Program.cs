using RTFExporter;
using System.Text;

string testsDir = @"C:\FILES\Egyetem\4_szemeszter\Projlab\projlab-error521\Fungorium\tests";
string outputFile = @"C:\FILES\Egyetem\4_szemeszter\Projlab\projlab-error521\Fungorium\tests.rtf";

RTFDocument doc = new RTFDocument();
Directory
    .GetDirectories(testsDir)
    .Select(x =>
    (
        group: x.Split('\\').Last(),
        tests: Directory.GetFiles(x).Where(x => x.EndsWith(".map")).Select(x => x.Split('.').First())
    ))
    .Index()
    .ToList()
    .ForEach(dir =>
    {

        var p = doc.AppendParagraph();
        p.style.spaceBefore = 240;
        var t = p.AppendText($"8.2.{dir.Index+1} {dir.Item.group}");
        t.style = new RTFTextStyle(false, true, 13, "Arial", Color.black);

        dir.Item.tests.Index().ToList().ForEach(test => 
        { 
            p = doc.AppendParagraph();
            p.style.spaceBefore = 240;
            t = p.AppendText($"8.2.{dir.Index+1}.{test.Index+1} {test.Item.Split('\\').Last()}");
            t.style = new RTFTextStyle(false, true, 13, "Arial", Color.black);

            var input =
                File.ReadAllLines(test.Item + ".map")
                .Concat(["", "end"])
                .Concat(File.ReadAllLines(test.Item + ".op"))
                .Concat(["exit"])
                .Aggregate((x, y) => x + "\n" + y);
            var output = File.ReadAllText(test.Item + ".res");

            addUl("Leírás");
            addASD();
            addUl("Ellenőrzött funkcionalitás, várható hibahelyek");
            addASD();
            addUl("Bemenet");
            addCode(input);
            addUl("Elvárt kimenet");
            addCode(output);
        });
    });


doc.paragraphs.ForEach(p => p.style.indent.firstLine = 0);
bool saved = false;
do
{
    try
    {
        File.WriteAllText(outputFile, RTFParser.ToString(doc), CodePagesEncodingProvider.Instance.GetEncoding("iso-8859-2")!);
        saved = true;
    }
    catch (Exception)
    {
        Console.WriteLine("Close Word!");
        Console.ReadKey();
    }
} while (!saved);

void addUl(string str)
{
    var p = doc.AppendParagraph();
    p.style.indent.left = .375f - .17f;
    p.AppendText("•  ");
    var t = p.AppendText(str);
    t.style = new RTFTextStyle(false, true, 12, "Times New Roman", Color.black);
}
void addASD()
{
    var p = doc.AppendParagraph();
    p.style.indent.left = .375f;
    p.style.alignment = Alignment.Justified;
    var t = p.AppendText("asd");
    t.style = new RTFTextStyle(false, false, 12, "Times New Roman", Color.black);
}
void addCode(string code)
{
    var p = doc.AppendParagraph();
    p.style.indent.left = .375f;
    var t = p.AppendText(code);
    t.style = new RTFTextStyle(false, false, 8, "Consolas", Color.black);
}