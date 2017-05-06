package com.omega.board.command;

import java.util.ArrayList;

import org.springframework.ui.Model;

import com.omega.board.dao.BDao;
import com.omega.board.dto.BDto;

public class BListCommand implements BCommand {

	@Override
	public void execute(Model model) {
		// TODO Auto-generated method stub
		
		BDao dao = new BDao();
		ArrayList<BDto> dtos = dao.list();
	    model.addAttribute("list", dtos);
	    System.out.println(model.toString());
	}

}
