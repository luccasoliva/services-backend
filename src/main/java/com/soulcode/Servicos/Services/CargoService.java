package com.soulcode.Servicos.Services;

import com.soulcode.Servicos.Models.Cargo;
import com.soulcode.Servicos.Repositories.CargoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Service
public class CargoService {

    @Autowired
    CargoRepository cargoRepository;



    @Cacheable("cargosCache")
    public List<Cargo> mostrarTodosCargos(){

        return cargoRepository.findAll();
    }

    @Cacheable(value = "cargosCache", key = "#idCargo")
    public Cargo mostrarCargoPeloId(Integer idCargo){
        Optional<Cargo> cargo = cargoRepository.findById(idCargo);
        return cargo.orElseThrow();
    }

    @CachePut(value = "cargosCache", key = "#cargo.idCargo")
    public Cargo cadastrarCargo(Cargo cargo){

        cargo.setIdCargo(null);

        return cargoRepository.save(cargo);
    }
    @CachePut(value = "cargosCache", key = "#cargo.idCargo")
    public Cargo editarCargo(Cargo cargo){

        return cargoRepository.save(cargo);
    }

    @CacheEvict(value = "cargosCache", key = "#idCargo")
    public void excluirCargo(Integer idCargo){

        cargoRepository.deleteById(idCargo);
    }

    @PostConstruct
    public void createCargo(){
        Cargo cargo = new Cargo();
        cargo.setNome("Gerente");
        cargo.setDescricao("Gerente de TI");

        Cargo cargo2 = new Cargo();
        cargo2.setNome("Analista");
        cargo2.setDescricao("Analista de TI");

        cargoRepository.save(cargo);
        cargoRepository.save(cargo2);
    }
}
