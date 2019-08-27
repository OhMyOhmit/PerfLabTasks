package perflab.tasks;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TaskFour {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws IOException {
		String path = args[0];
		File input = new File(path);
		int size = (int) Files.lines(input.toPath()).count();
		BufferedReader br = new BufferedReader(new FileReader(input));
		
		TimeStamp[] clients = new TimeStamp[size]; //время входа и выхода клиентов
		int max = 0; //переменная для обозначения максимального кол-ва людей в промежутке времени
		ArrayList<TimeStamp> finals = new ArrayList<TimeStamp>(); //Переменная, хранящая данные для вывода
		int current = max; //Переменная для обозначения нынешнего количества людей (счетчик)
		Date start; //Переменная для вывода. Начало промежутка
		Date end = null; //Конец промежутка
		
		String st;
		int i = 0;
		DateFormat sdf = new SimpleDateFormat("hh:mm");
		while((st = br.readLine()) != null) {
			String[] times = st.split(" ");
			try {
				clients[i] = new TimeStamp(sdf.parse(times[0]), sdf.parse(times[1].replace("\\n", "")));
				i++;
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		br.close();

		//Сортировка массивка таймстэмпов по времени входа клиентов по возрастанию
		for(i = 0; i < clients.length-1; i++)
			for(int j = i+1; j < clients.length; j++) {
				if(clients[i].getEnterTime().after(clients[j].getEnterTime())) {
					TimeStamp temp = clients[i];
					clients[i] = clients[j];
					clients[j] = temp;
				}
			}

		for(i = 0; i < clients.length; i++) {
			current = 0;
			start = clients[i].getEnterTime();
			for(int j = 0; j < clients.length; j++) {
				end = clients[j].getExitTime();
				
				if(!end.after(start)) //чтобы не сравнивать в случаях, если вход позже выхода предыдущего
					continue;
				
				if(!clients[i].getExitTime().after(clients[j].getExitTime())) //Если клиент зашёл, плюсуем количество
					current++;

				//Если клиент уже вышел, минусуем его
				if(!clients[i].getExitTime().after(clients[j].getEnterTime())) { 
					current--;
					break;
				}
				
				if(current > max) { //Если набралось кол-во больше максимального
					max = current;
					finals.clear(); //Чистим переменную ответа
					finals.add(new TimeStamp(start, end)); //Добавляем временной интервал
				} else if (current == max) {
					boolean has = false;
					for(int k = 0; k < finals.size(); k++) {
						TimeStamp ts = finals.get(k);
						if(!ts.getExitTime().after(end) && !ts.getExitTime().before(end)) //Проверяем, есть ли уже более длинный промежуток
							has = true;
						else if(start.after(ts.getEnterTime()) && start.before(ts.getExitTime()))
							finals.remove(k); //Убираем таймстэмп, если у нас есть с соприкасающимся временным промежутком
					}
					if(!has)
						finals.add(new TimeStamp(start, end));
				}
			}
		}
		
		for(TimeStamp ts : finals)
			System.out.print(ts.enter.getHours() + ":" + ts.enter.getMinutes() + " " + ts.exit.getHours() + ":" + ts.exit.getMinutes() + "\n");
		
	}
	
	public static class TimeStamp{
		
		private Date enter;
		private Date exit;
		
		public TimeStamp(Date enter, Date exit) {
			this.enter = enter;
			this.exit = exit;
		}

		public Date getEnterTime() {
			return enter;
		}
		
		public Date getExitTime() {
			return exit;
		}
	}
}
