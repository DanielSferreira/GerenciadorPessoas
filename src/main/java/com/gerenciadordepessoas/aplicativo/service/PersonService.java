package com.gerenciadordepessoas.aplicativo.service;

import com.gerenciadordepessoas.aplicativo.dto.request.PersonDTO;
import com.gerenciadordepessoas.aplicativo.dto.request.PhoneDTO;
import com.gerenciadordepessoas.aplicativo.dto.response.MessageResponseDTO;
import com.gerenciadordepessoas.aplicativo.entity.Person;
import com.gerenciadordepessoas.aplicativo.entity.Phone;
import com.gerenciadordepessoas.aplicativo.mapper.PhoneMapper;
import com.gerenciadordepessoas.aplicativo.repository.PhoneRepository;
import lombok.AllArgsConstructor;
import com.gerenciadordepessoas.aplicativo.exception.PersonNotFoundException;
import com.gerenciadordepessoas.aplicativo.mapper.PersonMapper;
import com.gerenciadordepessoas.aplicativo.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PersonService {

    private PersonRepository personRepository;
    private PhoneRepository phoneRepository;

    private final PersonMapper personMapper = PersonMapper.INSTANCE;
    private final PhoneMapper phoneMapper = PhoneMapper.INSTANCE;

    public MessageResponseDTO createPerson(PersonDTO personDTO)
    {
        Person personToSave = personMapper.toModel(personDTO);
        Person savedPerson = personRepository.save(personToSave);
        return createMessageResponse(savedPerson.getId(), "Created person with ID ");
    }

    public List<PersonDTO> listAll()
    {
        List<Person> allPeople = personRepository.findAll();
        return allPeople.stream()
                .map(personMapper::toDTO)
                .collect(Collectors.toList());
    }

    public PersonDTO findPersonById(Long id) throws PersonNotFoundException
    {
        Person person = verifyIfExists(id);
        return personMapper.toDTO(person);
    }

    public void deletePerson(Long id) throws PersonNotFoundException
    {
        verifyIfExists(id);
        personRepository.deleteById(id);
    }

    public MessageResponseDTO updatePersonById(Long id, PersonDTO personDTO) throws PersonNotFoundException
    {
        verifyIfExists(id);
        Person personToUpdate = personMapper.toModel(personDTO);
        Person updatedPerson = personRepository.save(personToUpdate);
        return createMessageResponse(updatedPerson.getId(), "Updated person with ID ");
    }


    private Person verifyIfExists(Long id) throws PersonNotFoundException
    {
        return personRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(id));
    }

    public MessageResponseDTO updatePhoneById(Long id, PhoneDTO phoneDTO) throws PersonNotFoundException
    {
        verifyIfPhoneExists(id);

        Phone phoneToUpdate = phoneMapper.toModel(phoneDTO);
        Phone updatedPerson = phoneRepository.save(phoneToUpdate);
        return createMessageResponse(updatedPerson.getId(), "Updated phone with ID ");
    }
    public PhoneDTO findPhoneById(Long id) throws PersonNotFoundException
    {
        Phone phone = verifyIfPhoneExists(id);
        return phoneMapper.toDTO(phone);
    }

    private Phone verifyIfPhoneExists(Long id) throws PersonNotFoundException
    {
        return phoneRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(id));
    }

    private MessageResponseDTO createMessageResponse(Long id, String message)
    {
        return MessageResponseDTO
                .builder()
                .message(message + id)
                .build();
    }
}
