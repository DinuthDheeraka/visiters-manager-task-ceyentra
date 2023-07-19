/**
 * @author :  Dinuth Dheeraka
 * Created : 7/9/2023 7:56 PM
 */
package com.ceyentra.springboot.visitersmanager.service.impl;

import com.ceyentra.springboot.visitersmanager.dto.VisitDTO;
import com.ceyentra.springboot.visitersmanager.dto.VisitorDTO;
import com.ceyentra.springboot.visitersmanager.entity.VisitEntity;
import com.ceyentra.springboot.visitersmanager.entity.VisitorEntity;
import com.ceyentra.springboot.visitersmanager.enums.EntityDbStatus;
import com.ceyentra.springboot.visitersmanager.exceptions.VisitorException;
import com.ceyentra.springboot.visitersmanager.repository.VisitorRepository;
import com.ceyentra.springboot.visitersmanager.service.VisitorService;
import com.ceyentra.springboot.visitersmanager.util.convert.CustomConvertor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class VisitorServiceImpl implements VisitorService {

    private final VisitorRepository visitorDAO;

    private final ModelMapper modelMapper;

    private final CustomConvertor<VisitEntity, VisitDTO> convertor;

    @Override
    public VisitorDTO saveVisitor(VisitorDTO visitorDTO) {

        try {

            visitorDTO.setVisitorId(0);
            visitorDTO.setDbStatus(EntityDbStatus.ACTIVE);
            return modelMapper.map(visitorDAO.save(modelMapper.map(visitorDTO,
                    VisitorEntity.class)),
                    VisitorDTO.class);

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public VisitorDTO updateVisitor(VisitorDTO visitorDTO) {

        try {

            Optional<VisitorEntity> optional = visitorDAO.findById(visitorDTO.getVisitorId());

            if (optional.isEmpty() || optional.get().getDbStatus() == EntityDbStatus.DELETED) {
                throw new VisitorException(String.format("Unable to Update Visitor Details for Associate ID - %d.", visitorDTO.getVisitorId()));
            }

            VisitorDTO currentVisitorDto = modelMapper.map(optional.get(), VisitorDTO.class);

            currentVisitorDto.setFirstName(visitorDTO.getFirstName() == null ?
                    currentVisitorDto.getFirstName() : visitorDTO.getFirstName());

            currentVisitorDto.setLastName(visitorDTO.getLastName() == null ?
                    currentVisitorDto.getLastName() : visitorDTO.getLastName());

            currentVisitorDto.setNic(visitorDTO.getNic() == null ?
                    currentVisitorDto.getNic() : visitorDTO.getNic());

            currentVisitorDto.setPhone(visitorDTO.getPhone() == null ?
                    currentVisitorDto.getPhone() : visitorDTO.getPhone());

            return modelMapper.map(visitorDAO.save(modelMapper.map(currentVisitorDto,
                    VisitorEntity.class)),
                    VisitorDTO.class);

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public VisitorDTO readVisitorById(int id) {

        try {

            Optional<VisitorEntity> byId = visitorDAO.findById(id);

            if (byId.isEmpty() || byId.get().getDbStatus() == EntityDbStatus.DELETED) {
                throw new VisitorException(String.format("Couldn't find Visitor with Associate ID - %d.", id));
            }
            return modelMapper.map(byId.get(), VisitorDTO.class);

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public VisitorDTO readVisitorByNic(String nic) {

        try {

            Optional<VisitorEntity> byNic = Optional.ofNullable(visitorDAO.findByNic(nic));

            if (byNic.isEmpty() || byNic.get().getDbStatus() == EntityDbStatus.DELETED) {
                throw new VisitorException(String.format("Couldn't find Visitor with Associate NIC - %s", nic));
            }

            return modelMapper.map(visitorDAO.findByNic(nic), VisitorDTO.class);


        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<VisitDTO> readAllVisitsByVisitorId(int id) {

        try {

            List<VisitDTO> visitorDTOS = new ArrayList<>();

            Optional<VisitorEntity> byId = visitorDAO.findById(id);

            if (byId.isEmpty() || byId.get().getDbStatus() == EntityDbStatus.DELETED) {
                throw new VisitorException(String.format("Unable to Process.Couldn't find Visitor with Associate ID - %d.", id));
            }


            Optional<VisitorEntity> visitor = Optional.ofNullable(visitorDAO.findVisitsByVisitorId(id));

            if(visitor.isPresent()){
                visitorDTOS = convertor.convert(visitor.get().getVisitList());
            }

            return visitorDTOS;

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public int updateVisitorDbStatusById(EntityDbStatus status, int id) {

        try{

            Optional<VisitorEntity> byId = visitorDAO.findById(id);

            if(byId.isEmpty()||byId.get().getDbStatus()==EntityDbStatus.DELETED){
                throw new VisitorException(String.format("Unable to Process.Couldn't find Visitor with Associate ID - %d.", id));
            }

            return visitorDAO.updateVisitorDbStatusById(status.name(), id);

        }catch (Exception e){
            throw e;
        }
    }

    @Override
    public List<VisitorDTO> findVisitorsByDbStatus(EntityDbStatus status) {

        try{

            return modelMapper.map(visitorDAO.findVisitorsByDbStatus(status.name()),
                    new TypeToken<ArrayList<VisitorDTO>>() {
                    }.getType());

        }catch (Exception e){
            throw e;
        }
    }
}
