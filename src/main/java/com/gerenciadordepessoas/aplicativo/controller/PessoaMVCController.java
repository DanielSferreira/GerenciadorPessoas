package com.gerenciadordepessoas.aplicativo.controller;

import com.gerenciadordepessoas.aplicativo.dto.request.PersonDTO;
import com.gerenciadordepessoas.aplicativo.dto.request.PhoneDTO;
import com.gerenciadordepessoas.aplicativo.exception.PersonNotFoundException;
import com.gerenciadordepessoas.aplicativo.service.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/v1/people")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PessoaMVCController {

    private PersonService personService;

    @GetMapping
    public ModelAndView IndexPage() {
        ModelAndView mv = new ModelAndView();
        mv.addObject("Person",personService.listAll());
        mv.setViewName("Index");
        return mv;
    }
    @GetMapping("/new")
    public ModelAndView PersonForm()
    {
        ModelAndView mv = new ModelAndView();
        mv.addObject("PersonDTO",new PersonDTO());
        mv.setViewName("Cadastro");
        return mv;
    }
    @PostMapping("/new")
    public String PersonFormPost(@ModelAttribute PersonDTO dto)
    {
        personService.createPerson(dto);
        return "redirect:";
    }

    @GetMapping("/edit/{id}")
    public ModelAndView EditPage(@PathVariable Long id) throws PersonNotFoundException {
        ModelAndView mv = new ModelAndView();
        PersonDTO pes = personService.findPersonById(id);
        pes.setBirthDate(formatDate(pes.getBirthDate()));
        mv.addObject("PersonDTO",pes);

        mv.setViewName("Editar");
        return mv;
    }

    private String formatDate(String birthDate) {
        String[] DateTemp = birthDate.split("-");
        return String.format("%s-%s-%s",DateTemp[2],DateTemp[1],DateTemp[0]);
    }

    @PostMapping("/update")
    public String PersonUpdateFormPost(@ModelAttribute PersonDTO dto) throws PersonNotFoundException
    {
        personService.updatePersonById(dto.getId(),dto);
        return "redirect:";
    }
    @GetMapping("/adicionarContato/{id}")
    public ModelAndView AdicionarContatoPage(@PathVariable long id) throws PersonNotFoundException
    {
        ModelAndView mv = new ModelAndView();
        PersonDTO pes = personService.findPersonById(id);
        mv.addObject("Person",pes);
        mv.addObject("Phone", new PhoneDTO());
        mv.addObject("link", "createPhone");
        mv.setViewName("AdicionarContato");
        return mv;
    }
    @GetMapping("/editContact/{idPerson}/{idContato}")
    public ModelAndView EditContactPage(@PathVariable long idPerson, @PathVariable long idContato) throws PersonNotFoundException
    {
        ModelAndView mv = new ModelAndView();
        PersonDTO pes = personService.findPersonById(idPerson);
        mv.addObject("Person",pes);
        mv.addObject("Phone", personService.findPhoneById(idContato));
        mv.addObject("link", "editPhone");
        mv.setViewName("AdicionarContato");
        return mv;
    }
    @PostMapping("/editPhone/{idp}")
    public String EditContactPost(@PathVariable long idp, @ModelAttribute PhoneDTO dto) throws PersonNotFoundException
    {
        PersonDTO pes = personService.findPersonById(idp);
        try
        { personService.updatePhoneById(dto.getId(), dto); }
        catch (Exception e)
        { System.out.println(e.getMessage()); }
        return "redirect:/v1/people/adicionarContato/"+pes.getId().toString();
    }
    @GetMapping("/delete/{idp}/{idperson}")
    public String DeleteContact(@PathVariable long idp, @PathVariable Long idperson) throws PersonNotFoundException
    {
        try
        {
            PersonDTO pes = personService.findPersonById(idperson);
            pes.setBirthDate(formatDate(pes.getBirthDate()));

            List<PhoneDTO> phones = pes.getPhones();
            phones.remove(personService.findPhoneById(idp));
            pes.setPhones(phones);

            personService.updatePersonById(idperson,pes);
        }
        catch (Exception e)
        { System.out.println(e.getMessage()); }
        return "redirect:/v1/people/adicionarContato/"+idperson.toString();
    }
    @PostMapping("/createPhone/{idp}")
    public String CreateContactPost(@PathVariable long idp, @ModelAttribute PhoneDTO dto) throws PersonNotFoundException
    {
        PersonDTO pes = personService.findPersonById(idp);

        PhoneDTO phone = new PhoneDTO();
            phone.setNumber(dto.getNumber());
            phone.setType(dto.getType());

        pes.getPhones().add(phone);
        pes.setBirthDate(formatDate(pes.getBirthDate()));
        personService.updatePersonById(pes.getId(),pes);
        return "redirect:/v1/people/adicionarContato/"+pes.getId().toString();
    }

    @GetMapping("/remover/{id}")
    public String remover(@PathVariable Long id) throws PersonNotFoundException
    {
        personService.deletePerson(id);
        return "redirect:/v1/people";
    }
}
