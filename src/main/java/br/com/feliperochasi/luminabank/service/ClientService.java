package br.com.feliperochasi.luminabank.service;

import br.com.feliperochasi.luminabank.dto.*;
import br.com.feliperochasi.luminabank.model.Address;
import br.com.feliperochasi.luminabank.model.Client;
import br.com.feliperochasi.luminabank.repository.AddressRepository;
import br.com.feliperochasi.luminabank.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    private final static Long ACTIVE_USER = 1L;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AddressRepository addressRepository;

    public List<DetailsClientDTO> findAllClients() {
        List<Client> listOfClientsActive = clientRepository.findAllByActiveEquals(ACTIVE_USER);
        return listOfClientsActive.stream().map(DetailsClientDTO::new).toList();
    }

    public DetailsClientDTO findClientById(Long clientId) {
        Client clientForReturn = clientRepository.getReferenceById(clientId);
        return new DetailsClientDTO(clientForReturn);
    }

    public DetailsAddressDTO findAddressById(Long idAddress) {
        Address addressForReturn = addressRepository.getReferenceById(idAddress);
        return new DetailsAddressDTO(addressForReturn);
    }

    public Client createClient(ClientRegisterDTO dto) {
        Client newClient = new Client(dto);
        clientRepository.save(newClient);
        return newClient;
    }

    public Address createAddressForClient(AddressRegisterDTO dto) {
        Client clientForAddress = clientRepository.getReferenceById(dto.clientId());
        Address newAddress = new Address(dto, clientForAddress);
        addressRepository.save(newAddress);
        return newAddress;
    }

    public DetailsClientDTO updateClient(ClientUpdateDTO dto) {
        Client clientForUpdate = clientRepository.getReferenceById(dto.clientId());
        clientForUpdate.updateInfoClient(dto);
        return new DetailsClientDTO(clientForUpdate);
    }

    public DetailsAddressDTO updateAddressOfClient(AddressUpdateClient dto) {
        Address addressForUpdate = addressRepository.getReferenceById(dto.addressId());
        addressForUpdate.updateInfoAddress(dto);
        return new DetailsAddressDTO(addressForUpdate);
    }

    public void deleteAddress(Long id) {
        addressRepository.deleteById(id);
    }

    public void inativeClient(Long id) {
        Client clientForInative = clientRepository.getReferenceById(id);
        clientForInative.inativeClient();
    }
}
