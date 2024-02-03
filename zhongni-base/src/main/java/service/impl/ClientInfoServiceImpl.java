package .service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import .entity.ClientInfo;
import .service.ClientInfoService;
import .mapper.ClientInfoMapper;
import org.springframework.stereotype.Service;

/**
* @author z1085
* @description 针对表【client_info(三方应用信息)】的数据库操作Service实现
* @createDate 2024-01-26 17:06:45
*/
@Service
public class ClientInfoServiceImpl extends ServiceImpl<ClientInfoMapper, ClientInfo>
    implements ClientInfoService{

}




