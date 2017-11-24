/**
 * Created by pm on 17-10-27.
 */
import * as TYPES from './Types'

function fetchVideo(mobile_url) {
  return (dispatch) => {
    fetch(mobile_url)
      .then((pageHtml) => {
        return pageHtml.text();
      }).then((pageHtml) => {
      const regex = /href="([\w|\W]*\.mp4)"/;
      const result = pageHtml.match(regex);
      if (result && result[1]) {
        const videoUri = result[1];
        // this.setState({
        //   videoUri
        // });
        dispatch({type: TYPES.FETCH_DETAILS_VIDEO_URL, videoUri});
      }
    });
  }
};

function fetchJson(url) {
  return async (dispatch) => {
    let textData, jsonData;

    // textData = await AsyncStorage.getItem(id);

    if (textData) {
      // alert('数据来自本地');
    } else {
      const rawData = await fetch(url);
      textData = await rawData.text();
      // alert('数据来自服务器');
    }

    // title, summary
    // "images": {
    //   "small": "http://img3.doubanio.com/view/movie_poster_cover/ipst/public/p494268647.webp",
    //     "large": "http://img3.doubanio.com/view/movie_poster_cover/lpst/public/p494268647.webp",
    //     "medium": "http://img3.doubanio.com/view/movie_poster_cover/spst/public/p494268647.webp"
    // },

    // 反序列化： "死的"字符串 => "活的"对象
    jsonData = JSON.parse(textData);
    jsonData.image = jsonData.images.large.replace('webp', 'jpg');

    // 序列化： "活的"对象 => "死的"字符串
    // const textData = JSON.stringify(jsonData);
    // AsyncStorage.setItem(id, textData);

    console.log("jsonData.mobile_url=" + jsonData.mobile_url);
    /*fetch(jsonData.mobile_url)
     .then((pageHtml) => {
     return pageHtml.text();
     }).then((pageHtml) => {
     const regex = /href="([\w|\W]*\.mp4)"/;
     const result = pageHtml.match(regex);
     if (result && result[1]) {
     const videoUri = result[1];
     dispatch({type: TYPES.FETCH_DETAILS_PAGE, data: jsonData, ready: true,videoUri});
     }
     });*/

    let pageHtml = await fetch(jsonData.mobile_url);
    pageHtml = await pageHtml.text();
    const regex = /href="([\w|\W]*\.mp4)"/;
    const result = pageHtml.match(regex);
    if (result && result[1]) {
      const videoUri = result[1];
      console.log("videoUri" + videoUri);
      dispatch({type: TYPES.FETCH_DETAILS_PAGE, data: jsonData, ready: true, videoUri});
    }

  }
}

export {
  fetchVideo,
  fetchJson
}