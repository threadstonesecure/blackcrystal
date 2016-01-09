import React from 'react';
import {Input, MenuItem} from 'react-bootstrap';
import $ from 'jquery';
import ResourceTypeDirectory from './ResourceTypeDirectory'
import ResourceTypeGit from './ResourceTypeGit'
import { resourcesURI } from '../../utils/Config'

const ResourceSelector = React.createClass({

    loadData() {
        return $.getJSON(resourcesURI());
    },
    getInitialState() {
        return {data: [], selected: this.props.selected};
    },

    onChange(event) {
        this.setState({selected: event.target.value});
    },

    componentDidMount(){
        this.loadData().success(function (data) {
            if (this.isMounted()) {
                this.setState({data: data});
                console.log("settgin state on resource selector")
            }
        }.bind(this))
    },
    render() {
        return (
            <div>
                <Input value={this.state.selected}
                       type="select"
                       label="Resource" placeholder="Resource"
                       onChange={this.onChange}>
                    {
                        this.state.data.map(function (item, i) {
                                return <option value={item.name} key={i}>{item.name}</option>
                            }
                        )}
                </Input>


            </div>
        );
    }
});
export default ResourceSelector;
