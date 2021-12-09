import os
MOD_ID = "primeval"

def get_asset_path():
    return (os.getcwd()+"/assets/")

def generate_standard_block(block_id):
    blockstate_file = open(get_asset_path()+MOD_ID+"/blockstates/"+block_id+".json", "w")
    block_model_file = open(get_asset_path()+MOD_ID+"/models/block/"+block_id+".json", "w")
    item_model_file = open(get_asset_path()+MOD_ID+"/models/item/"+block_id+".json", "w")
    
    blockstate_file.write("{\n\t\"variants\": {\n\t\t\"\": {\n\t\t\t\"model\": \""+MOD_ID+":block/"+block_id+"\"\n\t\t}\n\t}\n}")
    block_model_file.write("{\n\t\"parent\": \"minecraft:block/cube_all\",\n\t\"textures\": {\n\t\t\"all\": \""+MOD_ID+":block/"+block_id+"\"\n\t}\n}")
    item_model_file.write("{\n\t\"parent\": \""+MOD_ID+":block/"+block_id+"\"\n}")
    
    blockstate_file.close()
    block_model_file.close()
    item_model_file.close()
    
def generate_standard_item(item_id):
    item_model_file = open(get_asset_path()+MOD_ID+"/models/item/"+item_id+".json", "w")
    item_model_file.write("{\n\t\"parent\": \"minecraft:item/generated\",\n\t\"textures\": {\n\t\t\"layer0\": \""+MOD_ID+":item/"+item_id+"\"\n\t}\n}")
    item_model_file.close()
        
def generate_handheld_item(item_id):
    item_model_file = open(get_asset_path()+MOD_ID+"/models/item/"+item_id+".json", "w")
    item_model_file.write("{\n\t\"parent\": \"minecraft:item/handheld\",\n\t\"textures\": {\n\t\t\"layer0\": \""+MOD_ID+":item/"+item_id+"\"\n\t}\n}")
    item_model_file.close()
    
def generate_log_block(block_id):
    blockstate_file = open(get_asset_path()+MOD_ID+"/blockstates/"+block_id+".json", "w")
    block_model_file = open(get_asset_path()+MOD_ID+"/models/block/"+block_id+".json", "w")
    horizontal_block_model_file = open(get_asset_path()+MOD_ID+"/models/block/"+block_id+"_horizontal.json", "w")
    item_model_file = open(get_asset_path()+MOD_ID+"/models/item/"+block_id+".json", "w")
    
    blockstate_file.write("{\n\t\"variants\": {\n\t\t\"axis=x\": {\n\t\t\t\"model\": \""+MOD_ID+":block/"+block_id+"_horizontal\",\n\t\t\t\"x\": 90,\n\t\t\t\"y\": 90\n\t\t},\n\t\t\"axis=y\": {\n\t\t\t\"model\": \""+MOD_ID+":block/"+block_id+"\"\n\t\t},\n\t\t\"axis=z\": {\n\t\t\t\"model\": \""+MOD_ID+":block/"+block_id+"_horizontal\",\n\t\t\t\"x\": 90\n\t\t}\n\t}\n}")
    block_model_file.write("{\n\t\"parent\": \"minecraft:block/cube_column\",\n\t\"textures\": {\n\t\t\"end\": \""+MOD_ID+":block/"+block_id+"_top\",\n\t\t\"side\": \""+MOD_ID+":block/"+block_id+"\"\n\t}\n}")
    horizontal_block_model_file.write("{\n\t\"parent\": \"minecraft:block/cube_column_horizontal\",\n\t\"textures\": {\n\t\t\"end\": \""+MOD_ID+":block/"+block_id+"_top\",\n\t\t\"side\":\""+MOD_ID+":block/"+block_id+"\"\n\t}\n}")
    item_model_file.write("{\n\t\"parent\": \""+MOD_ID+":block/"+block_id+"\"\n}")
    
    blockstate_file.close()
    block_model_file.close()
    item_model_file.close()
    
    
def create_ore_set(ore_type):
    generate_standard_block(ore_type+"_ore_small")
    generate_standard_item("raw_"+ore_type+"_small")
    generate_standard_block(ore_type+"_ore_medium")
    generate_standard_item("raw_"+ore_type+"_medium")
    generate_standard_block(ore_type+"_ore_large")
    generate_standard_item("raw_"+ore_type+"_large")

generate_standard_item("ashes")