package com.dm.ticket.service;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.UnknownHostException;

@Service
public class FileServiceImpl implements FileService {

    Environment environment;

    @Autowired
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public String saveFile(MultipartFile file) throws IOException {
        ApplicationHome home = new ApplicationHome(getClass());
        File jarFile = home.getSource();
        String parentPath = jarFile.getParent();
        String path = parentPath + File.separator + "img";
        if (!FileUtil.exist(path)) {
            FileUtil.mkdir(path);
        }
        String filename = file.getOriginalFilename();
        assert filename != null;
        String[] split = filename.split("\\.");
        String suffix = split[split.length-1];
        File destFile = new File(path + File.separator + IdUtil.fastSimpleUUID() + "." + suffix);
        file.transferTo(destFile);
        return "http://" + getLocalhost() + ":" +environment.getProperty("local.server.port")
                + "/img/" + destFile.getName();
    }

    public String getLocalhost() throws UnknownHostException {
        return Inet4Address.getLocalHost().getHostAddress();
    }
}
