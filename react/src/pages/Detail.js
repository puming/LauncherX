/**
 * Created by sunny on 21/03/2017.
 */

import React, {Component} from 'react';
import {
  StyleSheet,
  View,
  Text,
  Image,
  ImageBackground,
  ActivityIndicator,
  AsyncStorage,
  TouchableOpacity,
  Linking,
} from 'react-native';
import {connect} from "react-redux";
import {bindActionCreators} from "redux";
import {fetchVideo, fetchJson} from "../actions/DetailAction";

const api = 'https://api.douban.com/v2/movie/subject';

class Detail extends Component {
  static navigationOptions = {
    title: '详情页',
  };

   componentDidMount() {
    const {id} = this.props.navigation.state.params;
    this.props.fetchJson(`${api}/${id}`);
  }

  playVideo = () => {
    const videoUri = this.props.videoUri;

    if (videoUri) {
      const {navigate} = this.props.navigation
      navigate('VideoView', {
        uri: videoUri,
      })
      // Linking.openURL(videoUri);
    } else {
      alert('正在获取预告片地址，请稍后重试');
    }
  };

  render() {
    const {title, summary, image} = this.props.data;
    return (
      <View>
        {
          this.props.ready ?
            <View>
              <TouchableOpacity onPress={this.playVideo}>
                <ImageBackground source={{uri: image}} style={styles.image}>
                  <Image source={require('../../images/play-icon.png')} style={styles.play}/>
                </ImageBackground>
              </TouchableOpacity>
              <Text>{title}</Text>
              <Text>{summary}</Text>
            </View>
            :
            <ActivityIndicator size="large" style={styles.loading}/>
        }
      </View>
    );
  }
}

const styles = StyleSheet.create({
  image: {
    width: 150,
    height: 222,
    justifyContent: 'center',
    alignItems: 'center',
  },
  loading: {
    marginTop: 100,
  },
  play: {
    width: 107,
    height: 107,
  }
});
export default connect(
  state => ({
    ready: state.detail.ready,
    data: state.detail.data,
    videoUri: state.detail.videoUri,
  }),
  dispatch => bindActionCreators({fetchVideo, fetchJson}, dispatch),
)(Detail);
