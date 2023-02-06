import axios from 'axios';
import apiClient from './apiClient';

const basicAuth = async (username, password) => {
  const token = `Basic ${window.btoa(username + ':' + password)}`;

  await axios.post(
    'http://localhost:8080/basicAuth',
    {
      /* 注意：第二引数はBodyなので、Bodyなしの場合、空のオブジェクトをセットする。 */
    },
    {
      headers: {
        Authorization: token,
      },
    }
  );

  apiClient.interceptors.request.use((config) => {
    config.headers.Authorization = token;
    return config;
  });

  // GETの場合、第二引数がoptionなのでこれでOK。
  // GETはOKで、POSTがNGになる、謎現象に見えてしまうので、要注意。
  //   const res = await axios.get('/test', {
  //     headers: {
  //       Authorization: `Basic a2lyaW46dGVzdA==`,
  //     },
  //   });

  return token;
};

const authService = {
  basicAuth,
};

export default authService;
