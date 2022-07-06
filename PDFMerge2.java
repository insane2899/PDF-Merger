import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.List;
import java.util.Arrays;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfReader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.File;
import java.io.FileNotFoundException;

public class PDFMerge2{

	private static void mergePDFFiles(List<String> inputPDFList,String outputName) throws Exception{
		Document document = new Document();
		int pageNo = 1;
		PdfCopy copy = new PdfCopy(document,new FileOutputStream(outputName));
		document.open();
		ArrayList<HashMap<String,Object>> outlines = new ArrayList<HashMap<String,Object>>();
		for(String s:inputPDFList){
			PdfReader reader = new PdfReader(s);
			reader.unethicalreading=true;
			HashMap<String,Object> newBookMark = new HashMap<>();
			newBookMark.put("Title",getNamePart(s));
			newBookMark.put("Action","GoTo");
			newBookMark.put("Page",String.format("%d Fit",pageNo));
			outlines.add(newBookMark);
			copy.addDocument(reader);
			pageNo += reader.getNumberOfPages();
			reader.close();
		}
		copy.setOutlines(outlines);
		for(HashMap<String,Object> map:outlines){
			for(Map.Entry<String,Object> e:map.entrySet()){
				System.out.println(e.getKey()+" "+(String)e.getValue());
			}
			System.out.println();
		}
		System.out.println("Pdf Files merged Successfully");
		document.close();
	}

	private static String getNamePart(String s){
		int beg = 0;
		for(int i=0;i<s.length();i++){
			if(s.charAt(i)=='-'){
				beg = i+1;
			}
		}
		return s.substring(beg,s.length()-4);
	}

	private static List<String> listPdfForFolder(final File folder)throws FileNotFoundException{
		List<String> fileNames = new ArrayList<String>();
		for(final File fileEntry: folder.listFiles()){
			if(fileEntry.isDirectory()){
				// Process Directory Files
			}
			else{
				fileNames.add(folder.getName()+"/"+fileEntry.getName());
			}
		}
		return fileNames;
	}

	private static String getBookNumber(String bookName){
		boolean beg = false,end=false;
		String ans = "";
		for(int i=0;i<bookName.length();i++){
			if(bookName.charAt(i)=='/'){
				beg = true;
			}
			if(beg && !end && bookName.charAt(i)>='0' && bookName.charAt(i)<='9'){
				ans+=bookName.charAt(i);
			}
			else if(bookName.charAt(i)=='-'){
				end=true;
				break;
			}
		}
		return ans;
	}

	private static List<String> processList(List<String> pdfList){
		String[][] sortedList = new String[pdfList.size()][2];
		for(int i=0;i<pdfList.size();i++){
			sortedList[i][0]=pdfList.get(i);
			sortedList[i][1]=getBookNumber(pdfList.get(i));
		}
		Arrays.sort(sortedList,(String[] a,String[] b)->{
			return Integer.parseInt(a[1])-Integer.parseInt(b[1]);
		});
		List<String> result = new ArrayList<>();
		for(int i=0;i<pdfList.size();i++){
			result.add(sortedList[i][0]);
		}
		return result;
	}

	public static void main(String[] args){
		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Enter the name of the directory containing the PDFs to be Merged.");
			String directory = br.readLine().trim();
			System.out.println("Enter the output pdf name:");
			String outputName = br.readLine().trim();
			File folder = new File(directory);
			List<String> inputPdfList = processList(listPdfForFolder(folder));

			mergePDFFiles(inputPdfList,outputName);
		}catch(Exception e){
			e.printStackTrace();
		}
	}



}