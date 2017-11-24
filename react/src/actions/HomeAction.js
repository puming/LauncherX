/**
 * Created by pm on 17-10-12.
 */
import * as TYPES from './Types';
import {fetchData} from '../utils/FetchUtils';
import {createAction} from 'redux-actions';
const api = 'https://api.douban.com/v2/movie/in_theaters';

/*export let fetchList = createAction(TYPES.FETCH_MOVE_LIST, (start = 0, count = 2) => {
 return fetch(`${api}?start=${start}&count=${count}`)
 .then((response) => response.text())
 .then((responseText) => {
 const json = JSON.parse(responseText);

 return json.subjects;
 }).then((data) => {
 for (let i = 0; i++;i<data.length){
 console.log(data[i].title);
 }
 return data
 })
 .catch((error) => {
 console.error(error);

 });
 });*/

//action 是一个用于描述已发生事件的普通对象。
const moveAction = (movie) => {
  return {
    type:TYPES.FETCH_MOVE_LIST,
    movie,
  };
};
export function fetchList(start=0,count=6) {
  return fetchData(start,count,moveAction)(api);
}

export function fetchMore(start=0,count=6) {
  return fetchData(start,count,moveAction)(api)
}
