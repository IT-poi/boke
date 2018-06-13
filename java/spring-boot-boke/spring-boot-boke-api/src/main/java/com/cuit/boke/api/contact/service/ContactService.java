package com.cuit.boke.api.contact.service;

import com.cuit.boke.api.contact.dto.ContactDTO;
import com.cuit.boke.api.contact.dto.ContactQueryDTO;
import com.cuit.boke.api.contact.dto.ContactReplayDTO;
import com.cuit.boke.contact.beans.entry.Contact;
import com.cuit.boke.contact.dao.ContactMapper;
import com.cuit.boke.page.PageCommonDTO;
import com.cuit.boke.utils.MailService;
import com.cuit.boke.utils.SensitiveWordFilter;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yinjk.web.core.exception.BizException;
import com.yinjk.web.core.util.BeanMapUtil;
import com.yinjk.web.core.vo.PageVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

@Service
public class ContactService {

    @Autowired
    private ContactMapper contactMapper;

    @Autowired
    private MailService mailService;

    @Autowired
    private SensitiveWordFilter sensitiveWordFilter;


    /**
     * 添加留言
     *
     * @param contactDTO 留言dto
     * @return 是否添加成功
     */
    public boolean add(ContactDTO contactDTO) {
        Contact contact = new Contact();
        BeanUtils.copyProperties(contactDTO, contact);
        contact.setCreateAt(new Date());
        String content = contact.getContent();
        //过滤敏感词
        content = sensitiveWordFilter.replaceSensitiveWord(content, SensitiveWordFilter.maxMatchType, "*");
        contact.setContent(content);
        contact.setReplay(0);
        contact.setIgnore(0);
        return contactMapper.insert(contact) == 1;
    }

    /**
     * 分页查询留言列表
     *
     * @param contactQueryDTO
     * @return
     */
    public PageVO<Contact> list(ContactQueryDTO contactQueryDTO, Integer userId){
        PageHelper.startPage(contactQueryDTO.getPageNum(), contactQueryDTO.getPageSize());
        PageHelper.orderBy("create_at desc");
        Page<Contact> page = (Page<Contact>) contactMapper.findBy(BeanMapUtil.beanToMap(contactQueryDTO));
        return new PageVO<>(page);
    }


    /**
     * 删除留言
     * @param contactId 留言id
     * @param userId 操作用户id
     */
    public void delete(Integer contactId, Integer userId) {
        contactMapper.deleteByPrimaryKey(contactId);
    }

    /**
     * 回复留言
     *
     * @param contactReplayDTO 留言dto
     * @throws BizException 异常
     */
    public void replay(ContactReplayDTO contactReplayDTO) throws BizException {
        Integer contactId = contactReplayDTO.getContactId();
        Contact contact = contactMapper.selectByPrimaryKey(contactId);
        String email = contact.getEmail();
        //发邮件
        String mailContext = "您好：" + contact.getName() + "！<br>" +
                "您在：<a href=\"http://193.112.112.136/\">Fool的个人博客</a>网站中给博主的留言有了回复！<br>" +
                "回复内容是：<br>" + contactReplayDTO.getReplayContext();
        mailService.sendMail(email, contact.getSubject(), mailContext);
        Contact contact1 = new Contact();
        contact1.setId(contactId);
        contact1.setReplay(1);
        contactMapper.updateByPrimaryKey(contact1);
    }

    /**
     * 忽略留言
     *
     * @param contactId 留言id
     * @param userId 操作者id
     * @throws BizException 异常
     */
    public void ignore(Integer contactId, Integer userId) throws BizException {
        Contact contact = new Contact();
        contact.setIgnore(1);
        contact.setId(contactId);
        contact.setUpdateAt(new Date());
        contact.setUpdateBy(userId);
        contactMapper.updateByPrimaryKey(contact);
    }
}
