
package com.videoadmin.api.inter;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class ApiLoginController
{
//    private Logger logger = LoggerFactory.getLogger(this.getClass());
//    
//    @Resource
//    private SysAccountService userService;
//    
//    @ApiOperation(value = "API用户登录", notes = "根据用户名和密码进行登录", httpMethod = "POST")
//    @RequestMapping(value = "/api/login", method = RequestMethod.POST)
//    @ResponseBody
//    public void apiLogin(@ApiParam(value = "Json参数", required = true)
//    @RequestBody
//    LoginUserInDto loginUser, HttpServletRequest request, HttpServletResponse response)
//        throws Exception
//    {
//        String result = "";
//        ApiDataResult apiDataResult = new ApiDataResult();
//        if (loginUser != null && !StringUtils.isEmpty(loginUser.getUsername())
//            && !StringUtils.isEmpty(loginUser.getPassword()))
//        {
//            Subject subject = SecurityUtils.getSubject();
//            UsernamePasswordToken userToken = new UsernamePasswordToken(loginUser.getUsername(),
//                MD5Util.md5(loginUser.getPassword(),SysConstants.MD5_SALT));
//            // MD5Util.md5(loginUser.getPassword(),SysConstants.MD5_SALT));
//            try
//            {
//                subject.login(userToken);
//                if (subject.isAuthenticated())
//                {
//                    // 当登录成功后，生成验证token
//                    String token = null;
//                    try
//                    {
//                        token = WebUtil.createNewToken(loginUser.getUsername());
//                    }
//                    catch (Exception e)
//                    {
//                        this.logger.error("令牌创建失败,失败原因:" + e.getMessage());
//                        apiDataResult.setRetCode(-1);
//                        apiDataResult.setRetMsg("令牌创建失败!");
//                    }
//                    LoginUserOutDto outDto = new LoginUserOutDto();
//                    outDto.setName(loginUser.getUsername());
//                    outDto.setToken(token);
//                    if (StringUtils.isEmpty(result))
//                    {
//                        apiDataResult.setRetCode(100);
//                        Map<Object, Object> resultMap = new HashMap<Object, Object>();
//                        resultMap.put("resultMap", outDto);
//                        apiDataResult.setDataMap(resultMap);
//                        // 将个人信息存储在session中
//                        request.getSession(true).setAttribute(SysConstants.USER_ACTIVITY_INFO,
//                            outDto);
//                    }
//                }
//                else
//                {
//                    apiDataResult.setRetCode(-1);
//                    apiDataResult.setRetMsg("用户登录验证失败!");
//                }
//            }
//            catch (Exception e)
//            {
//                this.logger.info("用户登录失败,异常信息:" + e.getMessage());
//                userToken.clear();
//                apiDataResult.setRetCode(-1);
//                apiDataResult.setRetMsg("用户登录失败，用户名或密码错误!");
//            }
//            WebUtil.apiObjectToJsonUtil(apiDataResult, response);
//        }
//    }
//    
//    
//    @ApiOperation( value = "api获得用户信息", notes = "根据url的id来获取用户详细信息",httpMethod = "GET")
//    @RequestMapping(value ="/api/getUser", method = RequestMethod.GET)
//    public void searchApiUser(@ApiParam(value="Json参数",required=true) @RequestParam String id,HttpServletResponse response) throws Exception
//    {
//        logger.info("api入参：id="+id);
//        ApiDataResult apiDataResult = new ApiDataResult();
//        SysAccount th = new SysAccount();
//        if(!StringUtils.isEmpty(id))
//        {
//            th = this.userService.getAccountById(Integer.valueOf(id));
//        }
//        apiDataResult.setRetCode(100);
//        Map<Object, Object> resultMap = new HashMap<Object, Object>();
//        resultMap.put("resultMap", th);
//        apiDataResult.setDataMap(resultMap);
//        WebUtil.apiObjectToJsonUtil(apiDataResult, response);
//    }
//    
//    
//    @RequestMapping("/api/upload")
//    public void upload(@RequestParam MultipartFile file,HttpServletResponse response) throws IOException
//    {
//        if (file.isEmpty()) {
//            throw new RRException("上传文件不能为空");
//        }
//        ApiDataResult apiDataResult = new ApiDataResult();
//        //上传文件
//        logger.info("开始上传-----");
//        String url = OSSFactory.build().upload(file.getBytes());
//        logger.info("上传结束-----");
//        Map<Object, Object> resultMap = new HashMap<Object, Object>();
//        resultMap.put("url", url);
//        apiDataResult.setDataMap(resultMap);
//        WebUtil.apiObjectToJsonUtil(apiDataResult, response);
//    }
}
